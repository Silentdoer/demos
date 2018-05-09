package test;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author liqi.wang
 * @version 1.0
 */
public class Entrance {
    public static final String filepath = "C:/Users/video_file";//video_file文件路径
    public static final String POST_URL = "https://v2-auth-api.visioncloudapi.com/liveness/silent_detection/stateless";

    public static void main(String[] args) throws IOException {

        /*HttpClient httpclient = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://localhost:8080/test1");
        FileBody fileBody = new FileBody(new File(filepath));
        MultipartEntity entity = new MultipartEntity();
        entity.addPart("video_file", fileBody);
        post.setEntity(entity);
        post.setHeader("Authorization", "AUTHORIZATION");//请将AUTHORIZATION替换为根据API_KEY和API_SECRET得到的签名认证串
        HttpResponse response = httpclient.execute(post);*/

        // TODO 新版本里的HttpClient的创建方式：
        /*RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000).build();
        HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();*/

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("http://localhost:8080/test1");
        //httpGet.setURI();
        //httpGet.setHeader("key", "value");
        //httpGet.setParams();  // 或者直接写在URL里
        HttpResponse response = httpClient.execute(httpGet);
        if(response.getStatusLine().getStatusCode() == 200){
            HttpEntity responseEntity = response.getEntity();  // 可以理解为响应体
            //InputStream content = entity.getContent();
            // TODO 输出的就是响应体的内容（是UTF-8编码）
            System.out.println(EntityUtils.toString(responseEntity));
        }else {
            System.out.println("出错");
        }

        HttpPost httpPost = new HttpPost("http://localhost:8080/test4");
//        httpPost.setURI();
//        httpPost.setHeader();
        HttpEntity requestEntity = new BasicHttpEntity();
        ((BasicHttpEntity) requestEntity).setContent(new ByteArrayInputStream(new byte[10]));
        httpPost.setEntity(requestEntity);  // TODO 请求体

        // TODO MultipartEntity也过期了，可以用MultipartEntityBuilder来替代
        //HttpEntity build = MultipartEntityBuilder.create().addPart().build();
        HttpEntity requestEntity2 = new MultipartEntity();

        /**
         * Http协议里对于Multipart的特殊部分：
         * 1.Content-Type是Multipart/form-data，它表示请求体里仍然是form-data的数据（pair），但是是multipart的，即每个pair都对于一个文件
         * 2.在Content-Type里parameter部分是boundary=-----------------------------7da2e536604c8之类的（可自定义），表示请求体里通过这个参数来分隔不同的part
         * 3.请求体里每个part通过boundary来分隔，但是请求体内又有类似header的东西（可以指示此part的内容类型、encoding类型等）
         * 总结：multipart/form-data和普通的其实没什么区别，一样是将所有的数据写到请求体里，不过可以在请求体里分块配置，然后content-type不同，但是content-length是请求体的总长度
         *
         * 注意boundary在每个part里前面会再加--，最后的时候最后面也会多加--
         */
    }
}
