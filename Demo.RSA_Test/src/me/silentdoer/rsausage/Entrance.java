package me.silentdoer.rsausage;

import me.silentdoer.rsausage.security.RsaRender;
import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Base64;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
public class Entrance {
    public static void main(String[] args) throws Exception {
        String rawData = "原始数据888xxxMM.tt是";
        RsaRender render = new RsaRender();
        render.init(4096);
        PublicKey publicKey = render.getPublicKey();
        byte[] publicKeyEncoded = render.getPublicKeyBytes();
        // true
        System.out.println(Arrays.equals(publicKeyEncoded, publicKey.getEncoded()));
        PrivateKey privateKey = render.getPrivateKey();
        byte[] privateKeyEncoded = render.getPrivateKeyBytes();
        // true
        System.out.println(Arrays.equals(privateKeyEncoded, privateKey.getEncoded()));
        // 客户端未必是以这种方式获取原始数据的字节流
        byte[] raw = rawData.getBytes(StandardCharsets.UTF_8);
        // 加密后得到的字节流
        byte[] rsaEncoded = render.encryptByRSA(raw);
        System.out.println("加密后的Hex字符串：" + Hex.encodeHexString(rsaEncoded));
        // Base64短多了
        System.out.println("加密后的Base64字符串：" + Base64.getEncoder().encodeToString(rsaEncoded));
        byte[] rsaDecoded = render.testDecryptByRSA(rsaEncoded);
        // 解密成功
        System.out.println("解密后的数据：" + new String(rsaDecoded, StandardCharsets.UTF_8));
    }
}
