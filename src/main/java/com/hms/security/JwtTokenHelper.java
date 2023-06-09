package com.hms.security;

import com.hms.utils.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtTokenHelper {
    public String getUsernameFromToken(String token)
    {
        log.info("Fetching username from token");
        return getClaimFromToken(token,Claims::getSubject);
    }
    public Date getExpirationDateFromToken(String token)
    {
        log.info("Fetching expiration from token");
        return getClaimFromToken(token,Claims::getExpiration);
    }
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver)
    {
        log.info("Fetching claim from token");
        final Claims claims=getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    private Claims getAllClaimsFromToken(String token)
    {
        log.info("Fetching all claim from token");
        return Jwts.parser().setSigningKey(Constants.SIGNING_KEY).parseClaimsJws(token).getBody();
    }
    private Boolean isTokenExpired(String token)
    {
        log.info("is token expired");
        final Date expiration=getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    public String generateToken(String userName,String userId)
    {
        log.info("Fetching generate token");
        return doGenerateToken(userName,userId);
    }
    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private String doGenerateToken(String subject, String userId) {
        log.info("do generate token");
        Claims claims=Jwts.claims().setSubject(subject);
        claims.put("userId",userId);
        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Constants.ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
                .signWith(SignatureAlgorithm.HS512, Constants.SIGNING_KEY).compact();
    }
    public Boolean validateToken(String token, UserDetails userDetails) {
        log.info("validate token");
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    public Integer getUserIdFromToken(HttpServletRequest request){
        log.info("Fetching user id from token");
        String header = request.getHeader(Constants.AUTHORIZATION);
        String token=null;
        if (header!=null && header.startsWith(Constants.TOKEN_PREFIX)){
            log.info("inside if of Fetching user id from token");
            token=header.replace(Constants.TOKEN_PREFIX,"");
            try {
                Claims body = Jwts.parser().setSigningKey(Constants.SIGNING_KEY)
                        .parseClaimsJws(token)
                        .getBody();
                return Integer.valueOf((String) body.get("userId"));
            } catch (Exception e){
                log.info("Error Occurred while getting userId from token");
                return null;
            }
        }
        return null;
    }
}

