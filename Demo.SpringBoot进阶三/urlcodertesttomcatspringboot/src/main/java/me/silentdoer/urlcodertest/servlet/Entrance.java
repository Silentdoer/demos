package me.silentdoer.urlcodertest.servlet;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
public class Entrance {
    public static void main(String[] args) throws IOException {
        File file = new File("E:\\Desktop\\Images\\mao.jpg");
        byte[] bytes = FileUtils.readFileToByteArray(file);
        // Base64.getEncoder()进行的编码最后有两个==号，开头似乎都是/9j/（URL的不是，/会转换为_）
        // Base64.getUrlEncoder()，看了下三个都是有两个==结尾
        // Base64.getMimeEncoder()的数据最长，似乎有换行？？但是还是以==结尾（两个是不怕的）
        //String s = Base64.getEncoder().encodeToString(bytes);
        //String s = Base64.getUrlEncoder().encodeToString(bytes);
        byte[] result = Base64.getMimeEncoder().encode(bytes);
        System.out.println(result[result.length - 1]);
        System.out.println(new String(result));
        System.out.println();
        File file1 = new File("E:/testssss.jpg");
        byte[] bytes1 = Arrays.copyOf(result, result.length - 1);
        System.out.println(new String(bytes1));
        byte[] decode = Base64.getMimeDecoder().decode(bytes1);
        System.out.println(decode.length);
        FileUtils.writeByteArrayToFile(file1, decode);
    }
}
