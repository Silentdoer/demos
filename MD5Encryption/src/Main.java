import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main {

    public static void main(String[] args) throws Throwable {
        /*String str = "王立奇";
        String result = digestWithMd5(str);*/
        File file = new File("V:\\Users\\Silentdoer\\Desktop\\QQ图片20171006075230.gif");
        String result = digestWithMD5(file);
        System.out.println(result);
    }

    /**
     * @param str 待加密的字符串
     * @return 以MD5加密后的字符串
     * @decription 以UTF-8编码获取待加密字符串的字节数组
     */
    public static String digestWithMd5(String str) {
        String result;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes("UTF-8"));
            byte[] digest = md.digest();
            // 1 表示是正数，-1表示是负数
            result = new BigInteger(1, digest).toString(16).toUpperCase();  // 以16进制表示
            if (result.length() < 32) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < 32 - result.length(); i++) {
                    sb.append('0');
                }
                sb.append(result);
                result = sb.toString();
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String digestWithMD5(File file) throws Exception {
        MessageDigest md;
        FileInputStream in = null;
        String result;
        byte buffer[] = new byte[4096];
        int len;
        try {
            md = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer)) != -1) {
                // 会将摘要信息存储在digest里面，这个摘要可以分步进行的
                md.update(buffer, 0, len);
            }
            BigInteger bigInteger = new BigInteger(1, md.digest());
            result = bigInteger.toString(16).toUpperCase();
            if (result.length() < 32) {
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < 32 - result.length(); i++) {
                    sb.append("0");
                }
                sb.append(result);
                result = sb.toString();
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if (in != null)
                in.close();
        }
    }
}
