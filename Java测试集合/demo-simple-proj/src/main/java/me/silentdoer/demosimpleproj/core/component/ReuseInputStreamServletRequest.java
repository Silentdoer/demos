package me.silentdoer.demosimpleproj.core.component;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 继承HttpServletRequest的包装类，实现对流数据的重新读取
 *
 * @author liqi.wang
 * @version 1.0.0
 */
public class ReuseInputStreamServletRequest extends HttpServletRequestWrapper {

    private final byte[] body;

    private final String contentType;

    private final String methodType;

    private String urlDecodedQueryString;

    /**
     * 这个charset不是指客户端的，而是Tomcat设置里的以什么编码来解码，Tomcat8以上默认用UTF-8
     */
    private Charset urlDecodeCharset;

    public ReuseInputStreamServletRequest(HttpServletRequest request) throws IOException {
        this(request, StandardCharsets.UTF_8);
    }

    public ReuseInputStreamServletRequest(HttpServletRequest request, Charset urlDecodeCharset) throws IOException {
        super(request);
        this.validate(request);
        this.body = IOUtils.toByteArray(request.getInputStream());
        if (org.apache.commons.lang3.StringUtils.isBlank(request.getContentType())) {
            this.contentType = MediaType.APPLICATION_JSON_UTF8_VALUE;
        } else {
            this.contentType = request.getContentType();
        }
        this.methodType = request.getMethod();
        // Tomcat8以上默认用的是UTF-8来codec
        this.urlDecodeCharset = urlDecodeCharset;

        // getQueryString()获得的是客户端发过来的原始数据已编码（经过Socket测试过，客户端会主动进行URLEncode不是tomcat去编码的）
        // 解码包括两部分，将%xx转换为字节（或先将不需要解码的数据转换为字节，然后将+号转换为空格的字节，将%xx转换为字节然后安排到对应的位子形成原始数据的字节流然后进行字符集的编码转换）
        this.urlDecodedQueryString = URLDecoder.decode(request.getQueryString() == null ? "" : request.getQueryString(), this.urlDecodeCharset.name());
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    /**
     * 线程安全，可多次并同时读取流
     */
    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream bais = new ByteArrayInputStream(this.body);

        return new ServletInputStream() {

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {
            }

            @Override
            public int read() {
                return bais.read();
            }

            @Override
            public int available() {
                return 1;
            }
        };
    }

    @Override
    public String getParameter(@NonNull String name) {
        final String key = name;

        String result;
        //region 通用部分
        // 只从查询字符串里取值aa=bb&mm=cc
        result = Arrays.stream(this.urlDecodedQueryString.split("&")).filter(p -> {
            String[] split = p.split("=");
            return split.length > 1 && key.equals(split[0]);
        }).findFirst().orElse(null);
        //endregion
        // POST+application/x-www-form-urlencoded特殊情况
        if (HttpMethod.POST.name().equalsIgnoreCase(this.methodType) && MediaType.APPLICATION_FORM_URLENCODED.isCompatibleWith(MediaType.valueOf(this.contentType))) {
            // 查询字符串里没有找到key，接着从请求体里找
            if (result == null) {
                String unEncoded = new String(this.body, this.urlDecodeCharset);
                String encoded = "";
                try {
                    encoded = URLDecoder.decode(unEncoded, this.urlDecodeCharset.name());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                // 这部分不属于多个方法的通用代码就适当冗余了
                result = Arrays.stream(encoded.split("&")).filter(p -> {
                    String[] split = p.split("=");
                    return split.length > 1 && key.equals(split[0]);
                }).findFirst().orElse(null);
            }
        }
        return result;
    }

    /**
     * 重写此方法以便getParameterMap()之后仍能保留请求体的inputStream可读，可精简
     */
    @Override
    public Map<String, String[]> getParameterMap() {
        // 有顺序要求，不能用HashMap
        Map<String, String[]> result = new LinkedHashMap<>();
        LinkedMultiValueMap<String, String> swap = new LinkedMultiValueMap<>();
        // aa=bb&cc=dd&ee
        Arrays.stream(this.urlDecodedQueryString.split("&")).parallel().forEachOrdered(p -> {
            String[] split = p.split("=");
            // aa= or aa
            if (split.length == 1) {
                swap.add(split[0], "");
            } else {
                swap.add(split[0], split[1]);
            }
        });
        // 特殊情况需要同时查找请求体数据
        if (HttpMethod.POST.name().equalsIgnoreCase(this.methodType) && MediaType.APPLICATION_FORM_URLENCODED.isCompatibleWith(MediaType.valueOf(this.contentType))) {
            String unEncoded = new String(this.body, this.urlDecodeCharset);
            String encoded = "";
            try {
                encoded = URLDecoder.decode(unEncoded, this.urlDecodeCharset.name());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Arrays.stream(encoded.split("&")).parallel().forEachOrdered(p -> {
                String[] split = p.split("=");
                // aa= or aa
                if (split.length == 1) {
                    swap.add(split[0], "");
                } else {
                    swap.add(split[0], split[1]);
                }
            });
        }
        swap.forEach((key, value) -> {
            String[] vals = new String[value.size()];
            result.put(key, value.toArray(vals));
        });
        return result;
    }

    /**
     * 线程安全，本身也是为了支持多次读取
     */
    @Override
    public BufferedReader getReader() {
        ByteArrayInputStream bais = new ByteArrayInputStream(this.body);
        return new BufferedReader(new InputStreamReader(bais, this.urlDecodeCharset));
    }

    /**
     * SpringMVC要求POST请求必须提供合法的Content-Type
     */
    private void validate(HttpServletRequest request) {
        if (HttpMethod.POST.name().equalsIgnoreCase(this.methodType)) {
            MediaType.parseMediaType(request.getContentType());
        }
    }

    public void setUrlDecodeCharset(Charset urlDecodeCharset) {
        this.urlDecodeCharset = urlDecodeCharset;
    }
}