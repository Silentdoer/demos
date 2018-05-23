package me.silentdoer.rsausage.security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 这种写法有点像PropertyEditor
 *
 * @author liqi.wang
 * @version 1.0.0
 */
public class RsaRender {
    /**
     * 步骤，可以写个枚举来说明步骤，0生成状态，1初始化生成公私钥
     */
    private RsaRenderStepEnum step = RsaRenderStepEnum.GENERATED;

    /**
     * 密钥长度，一般都是2048或4096
     */
    private int keySize = 4096;
    private final int modKey = 64;

    private PrivateKey privateKey;
    private PublicKey publicKey;

    private final String ALGORITHM_NAME = "RSA";
    /**
     * TODO 经过测试这个不能修改
     */
    private final String ALGORITHM_CIPHER = "RSA/ECB/PKCS1Padding";

    public RsaRender(int keySize) {
        init(keySize);
    }

    public RsaRender() {
    }

    public void reset() {
        this.step = RsaRenderStepEnum.GENERATED;
        this.keySize = 4096;
        this.privateKey = null;
        this.publicKey = null;
    }

    public void init(int keySize) {
        if (!this.step.lessThan(RsaRenderStepEnum.INITED)) {
            return;
        }
        if (keySize < modKey || keySize % modKey != 0) {
            throw new IllegalArgumentException("keySize必须大于0且为64的整数倍，最好是2048或4096");
        }
        this.keySize = keySize;
        KeyPair pair = this.generateKeyPair();
        this.publicKey = pair.getPublic();
        this.privateKey = pair.getPrivate();
        this.step = RsaRenderStepEnum.INITED;
    }

    public PublicKey getPublicKey() {
        if (this.step.lessThan(RsaRenderStepEnum.INITED)) {
            throw new IllegalStateException("状态异常，请查看是否还未初始化");
        }
        return this.publicKey;
    }

    public byte[] getPublicKeyBytes() {
        if (this.step.lessThan(RsaRenderStepEnum.INITED)) {
            throw new IllegalStateException("状态异常，请查看是否还未初始化");
        }
        return this.publicKey.getEncoded();
    }

    public PrivateKey getPrivateKey() {
        if (this.step.lessThan(RsaRenderStepEnum.INITED)) {
            throw new IllegalStateException("状态异常，请查看是否还未初始化");
        }
        return this.privateKey;
    }

    public byte[] getPrivateKeyBytes() {
        if (this.step.lessThan(RsaRenderStepEnum.INITED)) {
            throw new IllegalStateException("状态异常，请查看是否还未初始化");
        }
        return this.privateKey.getEncoded();
    }

    private KeyPair generateKeyPair() {
        KeyPairGenerator keyPairGenerator = null;
        try {
            /**
             * 通过算法名生成键值对的生成器
             */
            keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM_NAME);
        } catch (Exception ex) {
            // 这里其实不会抛异常，这个就像Charset.forName("UTF-8")一样，除非JDK本身就是错误的
            ex.printStackTrace();
        }
        /**
         * 通过制定大小来初始化生成器
         */
        keyPairGenerator.initialize(keySize);
        /**
         * 获取键值对，每次获取的都不一样，因此需要保存
         */
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 将publicKey.getEncoded()得到的字节数组反序列化为公钥
     */
    public PublicKey deserializePublicKey(byte[] encodedPublicKey) throws InvalidKeySpecException {
        // 注意，公钥和私钥用的类不一样
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(encodedPublicKey);
        KeyFactory factory = null;
        try {
            factory = KeyFactory.getInstance(this.ALGORITHM_NAME);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return factory.generatePublic(x509EncodedKeySpec);
    }

    /**
     * 将privateKey.getEncoded()得到的字节数组反序列化为私钥
     */
    public PrivateKey deserializePrivateKey(byte[] encodedPrivateKey) throws InvalidKeySpecException {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
        KeyFactory factory = null;
        try {
            factory = KeyFactory.getInstance(this.ALGORITHM_NAME);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return factory.generatePrivate(pkcs8EncodedKeySpec);
    }

    /**
     * 通过公钥加密（这一步由客户端执行，因为公钥由小贷分发，这里只是做测试）
     * 对方会先通过调用接口获得公钥，这个公钥无所谓泄露，因为服务端在分发公钥后是不再需要公钥的
     * <p>
     * 这个plainText是源数据转换为字节流后的数据
     */
    public byte[] encryptByRSA(byte[] plainText) throws Exception {
        PublicKey key = this.publicKey;
        Cipher cipher = Cipher.getInstance(this.ALGORITHM_CIPHER);
        // 加密模式，公钥
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(plainText);
    }

    public byte[] testEncryptByRSA(byte[] plainText) throws Exception {
        PublicKey key = this.publicKey;
        byte[] encoded = key.getEncoded();
        key = this.deserializePublicKey(encoded);
        Cipher cipher = Cipher.getInstance(this.ALGORITHM_CIPHER);
        // 加密模式，公钥
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(plainText);
    }

    /**
     * 解密，这一步确实是服务端要做的，客户端通过服务端分发的公钥进行RSA加密，然后服务端用私钥对对方加密后的数据进行解密（encode是编码的意思，也可以理解为加密）
     */
    public byte[] decryptByRSA(byte[] encodedText) throws Exception {
        PrivateKey key = this.privateKey;
        Cipher cipher = Cipher.getInstance(this.ALGORITHM_CIPHER);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(encodedText);
    }

    public byte[] testDecryptByRSA(byte[] encodedText) throws Exception {
        PrivateKey key = this.privateKey;
        byte[] encoded = key.getEncoded();
        key = this.deserializePrivateKey(encoded);
        Cipher cipher = Cipher.getInstance(this.ALGORITHM_CIPHER);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(encodedText);
    }

    /**
     * Render的步骤
     */
    private enum RsaRenderStepEnum {
        GENERATED(0), INITED(1);

        private int step;

        RsaRenderStepEnum(int step) {
            this.step = step;
        }

        public boolean lessThan(RsaRenderStepEnum stepEnum) {
            if (this.step < stepEnum.step) {
                return true;
            }
            return false;
        }
    }
}
