import javafx.util.Pair;
import me.silentdoer.security.RSAHelper;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.concurrent.Executors;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
public class RsaHelperEntrance {
    public static void main(String[] args) throws Exception {
        byte[] rawData = "四剧联动快捷方式接口666,,,555dddAA".getBytes(StandardCharsets.UTF_8);
        int keySize = 2048;
        //Pair<PublicKey, PrivateKey> pair = me.silentdoer.security.RSAHelper.generatePublicPrivateKeys(keySize);
        Pair<byte[], byte[]> pair = RSAHelper.generatePublicPrivateKeyBytes(keySize);
        PublicKey publicKey = RSAHelper.deserializePublicKey(pair.getKey());
        PrivateKey privateKey = RSAHelper.deserializePrivateKey(pair.getValue());
        byte[] encrypted = RSAHelper.encryptByRSA(publicKey, rawData);
        System.out.println("加密后的Base64字符串：" + Base64.getEncoder().encodeToString(encrypted));
        byte[] decrypted = RSAHelper.decryptByRSA(privateKey, encrypted);
        System.out.println("原始数据：".concat(new String(decrypted, StandardCharsets.UTF_8)));
    }
}
