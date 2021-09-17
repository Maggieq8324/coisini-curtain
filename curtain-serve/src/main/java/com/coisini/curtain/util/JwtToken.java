package com.coisini.curtain.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.*;

/**
 * @Description JWT工具类
 * @author coisini
 * @date Aug 18, 2021
 * @Version 1.0
 */
@Component
public class JwtToken {

    /**
     * key
     */
    private static String jwtKey;

    /**
     * 过期时间
     */
    private static Integer expiredTimeIn;

    /**
     * 默认等级
     */
    private static Integer defaultScope = 8;

    @Value("${coisini.security.jwt-key}")
    public void setJwtKey(String jwtKey) {
        JwtToken.jwtKey = jwtKey;
    }

    @Value("${coisini.security.token-expired-in}")
    public void setExpiredTimeIn(Integer expiredTimeIn) {
        JwtToken.expiredTimeIn = expiredTimeIn;
    }

    /**
     * 生成令牌
     * @param uid 用户id
     * @param scope 权限分级数字
     * @return
     */
    public static String makeToken(Long uid, Integer scope) {
        return JwtToken.getToken(uid, scope);
    }

    /**
     * 生成令牌
     * @param uid 用户id
     * @return
     */
    public static String makeToken(Long uid) {
        return JwtToken.getToken(uid, defaultScope);
    }

    /**
     * 获取令牌
     * @param uid 用户id
     * @param scope 权限分级数字
     * @return
     */
    private static String getToken(Long uid, Integer scope) {
        // 指定算法
        Algorithm algorithm = Algorithm.HMAC256(JwtToken.jwtKey);

        Map<String, Date> dateMap = JwtToken.calculateExpiredIssues();

        /**
         * withClaim(） 写入自定义数据
         * withExpiresAt() 设置过期时间
         * withIssuedAt() 设置当前时间
         * sign() 签名算法
         */
        return JWT.create()
                    .withClaim("uid", uid)
                    .withClaim("scope", scope)
                    .withExpiresAt(dateMap.get("expiredTime"))
                    .withIssuedAt(dateMap.get("now"))
                    .sign(algorithm);
    }

    /**
     * 获取自定义数据
     * @param token
     * @return
     */
    public static Optional<Map<String, Claim>> getClaims(String token) {
        DecodedJWT decodedJWT;

        // 指定算法
        Algorithm algorithm = Algorithm.HMAC256(JwtToken.jwtKey);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();

        try {
            decodedJWT = jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            return Optional.empty();
        }

        return Optional.of(decodedJWT.getClaims());
    }

    /**
     * 验证Token
     * @param token
     * @return
     */
    public static boolean verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JwtToken.jwtKey);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            return false;
        }

        return true;
    }

    /**
     * 计算过期时间
     * @return
     */
    private static Map<String, Date> calculateExpiredIssues() {
        Map<String, Date> map = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.add(Calendar.SECOND, JwtToken.expiredTimeIn);
        // 当前时间
        map.put("now", now);
        // 过期时间
        map.put("expiredTime", calendar.getTime());
        return map;
    }

    public static void main(String[] args) {
        System.out.println(JwtToken.getClaims("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjEsInNjb3BlIjo4LCJleHAiOjE3MTU5MjU4NTgsImlhdCI6MTYyOTUyNTg1OH0.b3imX6KvuuJlBdtChHsjHGjiQPWiPGI3p4e0aFtR4Vo"));
    }

}
