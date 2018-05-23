import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

//import org.apache.commons.codec.binary.Base64;

public class MyRSA {
    public static final String KEY_ALGORITHM = "RSA";
    /** 貌似默认是RSA/NONE/PKCS1Padding，未验证 */
    public static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
    public static final String PUBLIC_KEY = "publicKey";
    public static final String PRIVATE_KEY = "privateKey";

    /** RSA密钥长度必须是 64 的倍数，在512~65536之间。默认是1024，一般至少要2048以上 */
    public static final int KEY_SIZE = 4096;

    public static final String PLAIN_TEXT = "MANUTD is the greatest中文来来来 club in the world";

    // 密钥对的生成是随机的。（故对方调用自己RSA接口后要先判断有没有对应保存好的RSA公私钥，有则直接发公钥，否则再随机生成并保存发送）
    public static void main(String[] args) throws UnsupportedEncodingException {
        Long beforeTime, afterTime;
        beforeTime = System.currentTimeMillis();
        // 这里似乎有些多余的部分，就是将生成的PublicKey和PrivateKey转换为字节数组，后面又将这个字节数组转换回对应的key
        Map<String, byte[]> keyMap = generateKeyBytes();
        afterTime = System.currentTimeMillis();
        System.err.println("generateKeyBytes elapsed time: " + (afterTime - beforeTime));

        beforeTime = System.currentTimeMillis();
        byte[] publicKeyBuf = keyMap.get(PUBLIC_KEY);
        // 加密
        //PublicKey publicKey = restorePublicKey(publicKeyBuf);
        // test
        PublicKey publicKey = MyRSA.publicKeyRSA;
        afterTime = System.currentTimeMillis();
        System.out.println("restorePublicKey elapsed time: " + (afterTime - beforeTime));

        beforeTime = System.currentTimeMillis();
        byte[] originData = PLAIN_TEXT.getBytes("UTF-8");
        byte[] encryptedText = RSAEncrypt(publicKey, originData);
        System.out.println(String.format("[origin=%s,encrypted=%s]", originData.length, encryptedText.length));
        afterTime = System.currentTimeMillis();
        System.out.println("RSAEncode elapsed time: " + (afterTime - beforeTime));

        beforeTime = System.currentTimeMillis();
        //System.out.println("RSA encoded: " + (new BASE64Encoder()).encode(encryptedText) + "\n");
        // 加密是对字节数组加密，故加密字符串前先将它转换为字节数组（因为字符串它并不是一个固定字节数的东西，它跟编码密切关联）
        System.out.println("加密后得到的字节数组转换为UTF-8编码的字符串：" + new String(encryptedText, "UTF-8").intern());
        //System.out.println("RSA encoded: " + Base64.encodeBase64String(encryptedText));
        afterTime = System.currentTimeMillis();
        System.out.println("(new BASE64Encoder()).encode elapsed time: " + (afterTime - beforeTime));

        beforeTime = System.currentTimeMillis();
        // 这里试着将用于解密的私钥换成其它的“值”会报错
        byte[] privateKeyBuf = keyMap.get(PRIVATE_KEY);
        System.out.println(Arrays.equals(publicKeyBuf, privateKeyBuf));
        //privateKeyBuf[0] = (byte)1;
        // 解密
        //PrivateKey privateKey = restorePrivateKey(privateKeyBuf);
        // test
        PrivateKey privateKey = MyRSA.privateKeyRSA;
        afterTime = System.currentTimeMillis();
        System.out.println("restorePrivateKey elapsed time: " + (afterTime - beforeTime));

        beforeTime = System.currentTimeMillis();
        System.out.println("RSA decoded: " + new String(RSADecrypt(privateKey, encryptedText), "UTF-8").intern());
        afterTime = System.currentTimeMillis();
        System.out.println("RSADecode elapsed time: " + (afterTime - beforeTime));
    }

    // test
    private static PublicKey publicKeyRSA;
    private static PrivateKey privateKeyRSA;

    /**
     * 生成密钥对。注意这里是生成密钥对KeyPair，再由密钥对获取公私钥
     *
     * @return
     */
    public static Map<String, byte[]> generateKeyBytes() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator
                    .getInstance(KEY_ALGORITHM);
            keyPairGenerator.initialize(KEY_SIZE);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            // 注意这里产生的公钥和私钥都是随机的，每次都不同（目前没看到可以设置的地方，就是自动产生然后保存起来用即可）。
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

            // test
            MyRSA.publicKeyRSA = publicKey;
            MyRSA.privateKeyRSA = privateKey;

            Map<String, byte[]> keyMap = new HashMap<String, byte[]>();
            keyMap.put(PUBLIC_KEY, publicKey.getEncoded());
            keyMap.put(PRIVATE_KEY, privateKey.getEncoded());
            return keyMap;
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 还原公钥，X509EncodedKeySpec 用于构建公钥的规范
     *
     * @param keyBytes
     * @return
     */
    public static PublicKey restorePublicKey(byte[] keyBytes) {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey publicKey = factory.generatePublic(x509EncodedKeySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 还原私钥，PKCS8EncodedKeySpec 用于构建私钥的规范
     *
     * @param keyBytes
     * @return
     */
    public static PrivateKey restorePrivateKey(byte[] keyBytes) {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
                keyBytes);
        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateKey = factory
                    .generatePrivate(pkcs8EncodedKeySpec);
            return privateKey;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密，三步走。
     *
     * @param key
     * @param plainText
     * @return
     */
    public static byte[] RSAEncrypt(PublicKey key, byte[] plainText) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(plainText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密，三步走。
     *
     * @param key
     * @param encodedText
     * @return
     */
    public static byte[] RSADecrypt(PrivateKey key, byte[] encodedText) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(encodedText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}