package com.wcreators.jwt_starter.services.jwt;

import com.wcreators.jwt_starter.services.model.JwtUser;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Service
public class JwtProvider {

    private final String secret;

    public JwtProvider(@Value("$(jwt.secret)") String secret) {
        this.secret = secret;
    }

    public String generateToken(JwtUser user) {
        val date = Date.from(LocalDate.now().plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setAudience(user.getRole())
                .setId(user.getId().toString())
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired");
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt");
        } catch (Exception e) {
            log.error("Token invalid");
        }
        return false;
    }

    public String getRoleFromToken(String token) {
        val claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims.getAudience();
    }

    public Long getIdFromToken(String token) {
        val claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return Long.valueOf(claims.getId());
    }
}
