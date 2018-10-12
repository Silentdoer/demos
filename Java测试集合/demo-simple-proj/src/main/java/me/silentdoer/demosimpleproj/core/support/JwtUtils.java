package me.silentdoer.demosimpleproj.core.support;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

/**
 * @author liqi.wang
 * @version 1.0.0
 * @date 9/28/2018 7:11 PM
 */
public class JwtUtils {

    /**
     * 生成JWT token用到的Security key
     */
    private static final String SECURE_KEY = "9cb35b0c67ef6310f38fb0fb5e98448f091245e2866f9d27a169eba445de7e9a";

    private JwtUtils() {}

    /**
     * 校验token是否正确
     *
     * @param token token
     * @param key key
     * @param timestamp 产生此token的timestamp
     * @return token和key及timestamp是否匹配
     */
    public static boolean verify(String token, String key, long timestamp) {
        try {
            //根据密码生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC256(SECURE_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("key", key).withClaim("timestamp", timestamp).build();
            //效验TOKEN
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 校验token是否正确
     *
     * @param token token
     * @param key key
     * @return token和key及timestamp是否匹配
     */
    public static boolean verify(String token, String key) {
        try {
            //根据密码生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC256(SECURE_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("key", key).build();
            //效验TOKEN
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的key
     */
    public static String getTokenKey(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("key").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的产生token的时间戳
     */
    public static Long getTokenTimestamp(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("timestamp").asLong();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名
     *
     * @param key 用户名或fId等
     * @param timestamp 生成JWT token的时间戳
     * @return 加密的token
     */
    public static String sign(String key, long timestamp) {
        Algorithm algorithm = Algorithm.HMAC256(SECURE_KEY);
        // 附带username信息
        return JWT.create().withClaim("key", key)
                .withClaim("timestamp", timestamp).sign(algorithm);
    }

    /**
     * 生成签名
     *
     * @param key 用户名或fId等
     * @return 加密的token
     */
    public static String sign(String key) {
        Algorithm algorithm = Algorithm.HMAC256(SECURE_KEY);
        // 附带username信息
        return JWT.create().withClaim("key", key).sign(algorithm);
    }

    public static void main(String[] args) {
        String token = JwtUtils.sign("si我", System.currentTimeMillis());
        System.err.println(token);
        System.err.println(JwtUtils.getTokenKey(token));
        System.err.println(new Date(JwtUtils.getTokenTimestamp(token)));
        System.err.println(JwtUtils.verify(token, "si3我", JwtUtils.getTokenTimestamp(token)));
    }
}