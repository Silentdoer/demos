package me.silentdoer.aesusage;

import me.silentdoer.aesusage.security.AESHelper;
import org.apache.commons.lang3.RandomStringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
public class Entrance {
    public static void main(String[] args) throws Exception {
        /**
         * 只是产生SecretKey时有用
         */
        byte[] seed = RandomStringUtils.randomAlphanumeric(8).getBytes(StandardCharsets.UTF_8);
        byte[] secretKey = AESHelper.generateSecretKey(seed);
        seed = null;
        String rawData = "荷花是uio8mm们";
        byte[] encryptedData = AESHelper.encryptByAES(secretKey, rawData.getBytes(StandardCharsets.UTF_8));
        System.out.println(Base64.getMimeEncoder().encodeToString(encryptedData));
        byte[] decryptedData = AESHelper.decryptByAES(encryptedData, secretKey);
        System.out.println(new String(decryptedData, StandardCharsets.UTF_8));
    }
}
