package com.company.demo.service;

import com.company.demo.config.JwtConfig;
import com.company.demo.exception.EntitySaveException;
import com.company.demo.model.Token;
import com.company.demo.model.User;
import com.company.demo.repository.TokenRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtTokenService {

    private final JwtConfig jwtConfig;
    private final TokenRepository tokenRepository;

    public String generateToken(User user) {
        final Date createdDate = new Date();
        final Date expirationDate = calculateExpirationDate(createdDate);

        HashMap<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        claims.put("email", user.getEmail());
        claims.put("name", user.getName());
        claims.put("role", user.authorities());

        String jwt = Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret())
                .compact();

        Token token = Token.builder()
                .token(jwt).user(user).invalidated(0)
                .expiredAt(convertDateToLocalDateTime(expirationDate))
                .loginAt(LocalDateTime.now())
                .build();

        try {
            tokenRepository.save(token);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new EntitySaveException("JWT token save failed.");
        }

        return jwt;
    }

    private LocalDateTime convertDateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public boolean validateToken(String authToken) {

        Token token = tokenRepository.findByToken(authToken);
        if (token == null || token.getInvalidated().equals(1)) {
            return false;
        }

        try {
            Jwts.parser().setSigningKey(jwtConfig.getSecret()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + jwtConfig.getExpiration() * 1000);
    }

    public String getJwtFromServletRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
