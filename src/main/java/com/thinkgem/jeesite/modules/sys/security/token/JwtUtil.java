package com.thinkgem.jeesite.modules.sys.security.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class JwtUtil {
    private static Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    //JWT验证过期时间
    private static final long EXPIRE_TIME = 1000 * 60 * 60 * 24;

    public static String creatToken(String policeId, String cardId) {
        Date date = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24);
        String token = "";
        try {
            token = JWT.create().withAudience(policeId)
                    .withExpiresAt(date)
                    .sign(Algorithm.HMAC256(cardId));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return token;
    }

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String username, String secret) {
        try {
            //根据密码生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).build();
            //效验TOKEN
            DecodedJWT jwt = verifier.verify(token);
            logger.info("JwtUtil登录验证成功!");
            return true;
        } catch (Exception exception) {
            logger.error("JwtUtil登录验证失败!");
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        String loginName = JWT.decode(token).getAudience().get(0);
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
    /*获取登录名*/
    public static String getLoginName(String token) {
        try {
            return JWT.decode(token).getAudience().get(0);
        }catch (JWTDecodeException e){
            return null;
        }

    }

    /**
     * 生成token签名EXPIRE_TIME 分钟后过期
     *
     * @param username 用户名(手机号)
     * @param secret   用户的密码
     * @return 加密的token
     */
    public static String sign(String username, String secret) throws UnsupportedEncodingException {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create().withClaim("username", username).withExpiresAt(date).sign(algorithm);
    }
}
