package me.silentdoer.demosimpleproj.core.util;

import lombok.SneakyThrows;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
public final class AesUtils {

    /**
     * 通过密钥将数据进行加密
     */
    public static byte[] encryptByAES(byte[] secretKey, byte[] rawData) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        return cipher.doFinal(rawData);
    }

    /**
     * 通过密钥将AES加密后的数据解密
     */
    public static byte[] decryptByAES(byte[] encryptedData, byte[] secretKey) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return cipher.doFinal(encryptedData);
    }

    /**
     * 通过seed获取AES的密钥
     */
    @SneakyThrows(NoSuchAlgorithmException.class)
    public static byte[] generateSecretKey(byte[] seed) {
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(seed);
        keygen.init(128, secureRandom);
        SecretKey secretKey = keygen.generateKey();
        return secretKey.getEncoded();
    }
}