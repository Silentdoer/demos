package me.silentdoer.urlcodertest.servlet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
public class TestServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding(StandardCharsets.UTF_8.name());
        //ServletInputStream inputStream = request.getInputStream();
        //byte[] bytes = IOUtils.toByteArray(inputStream);
        //System.out.println(new String(bytes, "UTF-8"));
        Map<String, String[]> parameterMap = request.getParameterMap();
        System.out.println(parameterMap.size());

        Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
        for(Map.Entry<String, String[]> entry : entries){
            //System.out.println(entry.getKey() + "分隔符" + entry.getValue().toString());
            System.out.println(entry.getKey());
            // MessageFormat也可以不指定顺序，就像log.info("{},{}", ..)的用法一样
            System.out.println(MessageFormat.format("{1},{0}", Arrays.asList(entry.getValue()) , entry.getValue().length));
            for(int i = 0; i < entry.getValue().length; i++) {
                System.out.println((entry.getValue()[i] == null) + "#" + (entry.getValue()[i].equals("")));
            }
        }
        /*ServletInputStream inputStream = request.getInputStream();
        String queryString = request.getQueryString();
        System.out.println(queryString);
        byte[] buffer = new byte[1024];
        int count = inputStream.read(buffer);
        String str = new String(Arrays.copyOf(buffer, count), request.getCharacterEncoding());
        System.out.println(str);
        //System.out.println(parameterMap.toString());*/
        /*System.out.println(request.getParameter("keys"));
        System.out.println(request.getQueryString());*/
        System.out.println(request.getContentType() + "  " + request.getCharacterEncoding());
        PrintWriter writer = response.getWriter();
        writer.append("MMMMM");
        writer.close();
    }
}
