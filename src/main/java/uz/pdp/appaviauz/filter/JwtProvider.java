package uz.pdp.appaviauz.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JwtProvider {
    @Value("${command.expire-time}")
    Long expiration;
    @Value("${command.jwt-key}")
    String secretKey;

    public String generateJWT(String subject) {
        String compact = "";
        try {
            compact = Jwts.builder()
                    .setSubject(subject)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration))
                    .signWith(SignatureAlgorithm.HS512, secretKey)
                    .compact();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return compact;
    }

    public String getSubjectFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
