package me.silentdoer.rsausage;

import javafx.util.Pair;
import me.silentdoer.rsausage.security.RsaHelper;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
public class RsaHelperEntrance {
    public static void main(String[] args) throws Exception {
        byte[] rawData = "四剧联动快捷方式接口666,,,555dddAA".getBytes(StandardCharsets.UTF_8);
        int keySize = 4096;
        //Pair<PublicKey, PrivateKey> pair = RsaHelper.generatePublicPrivateKeys(keySize);
        Pair<byte[], byte[]> pair = RsaHelper.generatePublicPrivateKeyBytes(keySize);
        PublicKey publicKey = RsaHelper.deserializePublicKey(pair.getKey());
        PrivateKey privateKey = RsaHelper.deserializePrivateKey(pair.getValue());
        byte[] encrypted = RsaHelper.encryptByRSA(publicKey, rawData);
        System.out.println("加密后的Base64字符串：" + Base64.getEncoder().encodeToString(encrypted));
        byte[] decrypted = RsaHelper.decryptByRSA(privateKey, encrypted);
        System.out.println("原始数据：".concat(new String(decrypted, StandardCharsets.UTF_8)));
    }
}
