package com.lj.app.core.common.generator.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {
    static final String SECRET = "ThisIsASecret";
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "TOKEN";
    
    public static String generateToken(String username,String userId, Long jtwTokenTimeOut) {
        HashMap<String, Object> map = new HashMap<>();
        //you can put any data in the map
        map.put("USER_NAME", username);
        map.put("USER_ID", userId);
        String jwt = Jwts.builder()
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + jtwTokenTimeOut*60*24))// 24h
                //.setExpiration(new Date(System.currentTimeMillis() + jtwTokenTimeOut))// 24h
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        return "Bearer "+jwt; //jwt前面一般都会加Bearer
    }
    

    public static void validateToken(String token) {
        try {
        	Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace("Bearer ",""))
                    .getBody();
        }catch (Exception e){
            throw new IllegalStateException("Invalid Token. "+e.getMessage());
        }
    }

    public static Map<String, Object> validateTokenAndGetClaims(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (StringUtil.isBlank(token)) {
            token = request.getParameter(HEADER_STRING);
        }

        if (token == null)
            throw new TokenValidationException("Missing token");
        // parse the token. exception when token is invalid
       return  Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody();
    
    }

    static class TokenValidationException extends RuntimeException {
        public TokenValidationException(String msg) {
            super(msg);
        }
    }
}