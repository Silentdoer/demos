import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
public class Entrance {
    public static void main(String[] args) throws UnsupportedEncodingException, EncoderException {
        String str = "EuHAo4OG5wHT/hjhNmPUOx/5JIP+BvD2SOpepmzPObwxwzgTwRvnA jh";
        String result = "";
        result = URLDecoder.decode(str, "UTF-8");
        System.out.println(result);
        result = URLEncoder.encode(str, "UTF-8");
        System.out.println(result);
        org.apache.commons.codec.net.URLCodec enc = new URLCodec();
        String encode = enc.encode(str);
        System.out.println(encode);
        System.out.println(URLEncoder.encode(str, StandardCharsets.UTF_8.name()));
    }
}
