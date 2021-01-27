package com.wcreators.todo_api.configs.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Log
public class JwtProvider {

    @Value("$(jwt.secret)")
    private String secret;

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
//            log.severe("Token expired");
        } catch (UnsupportedJwtException unsEx) {
//            log.severe("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
//            log.severe("Malformed jwt");
        } catch (Exception e) {
//            log.severe("Token invalid");
        }
        return false;
    }

    public String getRoleFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims.getAudience();
    }

    public Long getIdFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return Long.valueOf(claims.getId());
    }
}
