package me.silentdoer.security;

import javafx.util.Pair;
import lombok.SneakyThrows;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA算法的工具类
 *
 * @author liqi.wang
 * @version 1.0.0
 */
public final class RSAHelper {
    private static final String ALGORITHM_NAME = "RSA";
    private static final String ALGORITHM_CIPHER = "RSA/ECB/PKCS1Padding";
    private static final int modKey = 64;

    private RSAHelper() {
    }

    @SneakyThrows(NoSuchAlgorithmException.class)
    public static KeyPair generateKeyPair(final int keySize) {
        if (keySize < modKey || keySize % modKey != 0) {
            throw new IllegalArgumentException("keySize必须大于0且为64的整数倍，最好是2048或4096");
        }

        /**
         * 通过算法名生成键值对的生成器
         */
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSAHelper.ALGORITHM_NAME);
        /**
         * 通过制定大小来初始化生成器，这个assert只是起到suppressWarning的作用
         */
        keyPairGenerator.initialize(keySize);
        /**
         * 获取键值对，每次获取的都不一样，需要保存
         */
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 结果的pair的getKey()是PublicKey，getValue()是PrivateKey
     */
    public static Pair<PublicKey, PrivateKey> generatePublicPrivateKeys(final int keySize) {
        KeyPair keyPair = RSAHelper.generateKeyPair(keySize);
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        return new Pair<>(publicKey, privateKey);
    }

    public static Pair<byte[], byte[]> generatePublicPrivateKeyBytes(final int keySize) {
        KeyPair keyPair = RSAHelper.generateKeyPair(keySize);
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        return new Pair<>(publicKey.getEncoded(), privateKey.getEncoded());
    }

    /**
     * 将publicKey.getEncoded()的字节流反序列化为PublicKey对象
     */
    @SneakyThrows(NoSuchAlgorithmException.class)
    public static PublicKey deserializePublicKey(final byte[] encodedPublicKey) throws InvalidKeySpecException {
        // 注意，公钥和私钥用的类不一样
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(encodedPublicKey);
        KeyFactory factory = KeyFactory.getInstance(RSAHelper.ALGORITHM_NAME);
        return factory.generatePublic(x509EncodedKeySpec);
    }

    /**
     * private.getEncoded()的字节流反序列化为PrivateKey对象
     */
    @SneakyThrows(NoSuchAlgorithmException.class)
    public static PrivateKey deserializePrivateKey(final byte[] encodedPrivateKey) throws InvalidKeySpecException {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
        KeyFactory factory = KeyFactory.getInstance(RSAHelper.ALGORITHM_NAME);
        return factory.generatePrivate(pkcs8EncodedKeySpec);
    }

    /**
     * 以公钥对字节流进行加密
     */
    public static byte[] encryptByRSA(PublicKey publicKey, byte[] plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(RSAHelper.ALGORITHM_CIPHER);
        // 加密模式，公钥
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(plainText);
    }

    public static byte[] encryptByRSA(byte[] publicKey, byte[] plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(RSAHelper.ALGORITHM_CIPHER);
        // 加密模式，公钥
        cipher.init(Cipher.ENCRYPT_MODE, RSAHelper.deserializePublicKey(publicKey));
        return cipher.doFinal(plainText);
    }

    /**
     * 以私钥对加密后的字节流进行解密
     */
    public static byte[] decryptByRSA(PrivateKey privateKey, byte[] encodedText) throws Exception {
        Cipher cipher = Cipher.getInstance(RSAHelper.ALGORITHM_CIPHER);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(encodedText);
    }

    public static byte[] decryptByRSA(byte[] privateKey, byte[] encodedText) throws Exception {
        Cipher cipher = Cipher.getInstance(RSAHelper.ALGORITHM_CIPHER);
        cipher.init(Cipher.DECRYPT_MODE, RSAHelper.deserializePrivateKey(privateKey));
        return cipher.doFinal(encodedText);
    }
}