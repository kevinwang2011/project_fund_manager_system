package com.meizx.project_fund_manager_system.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.meizx.project_fund_manager_system.dao.po.UserPO;
import java.util.Date;

/**
 * @Author: meizx
 * @ClassName: TokenUtil
 * @Date: 2020/4/28 10:27
 * @E-mail： meizxbj@gmail.com
 * @Description: TokenUtil
 * @Version: V1.0.0
 */
public class TokenUtil {

    private static final long EXPIRE_TIME= 2*60*60*1000;
    private static final String TOKEN_SECRET="txdy";  //密钥盐

    /**
     * 签名生成
     * @param user
     * @return
     */
    public static String sign(UserPO user){
        String token = null;
        try {
            Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("userId", user.getUserId())
                    .withExpiresAt(expiresAt)
                    // 使用了HMAC256加密算法。
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (Exception e){
            e.printStackTrace();
        }
        return token;
    }

    /**
     * 签名验证
     * @param token
     * @return
     */
    public static boolean verify(String token){
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
            DecodedJWT jwt = verifier.verify(token);
            System.out.println("认证通过：");
            System.out.println("userId: " + jwt.getClaim("userId").asString());
            System.out.println("过期时间：      " + jwt.getExpiresAt());
            return true;
        } catch (Exception e){
            return false;
        }
    }

    /**
     * 签名验证
     * @param token
     * @return userId
     */
    public static String verify(String token, int n){
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("userId").asString();
        } catch (Exception e){
            return "";
        }
    }
}
