package com.example.springSecurity.common.utility;

import static com.example.springSecurity.common.exception.ErrorCode.EXPIRED_TOKEN_ERROR;
import static com.example.springSecurity.common.exception.ErrorCode.INVALID_TOKEN_ERROR;
import static com.example.springSecurity.common.exception.ErrorCode.TOKEN_UNSUPPORTED_ERROR;

import com.example.springSecurity.common.constant.JwtConstants;
import com.example.springSecurity.common.exception.CommonException;
import com.example.springSecurity.config.security.app.dto.response.DefaultJsonWebTokenDto;
import com.example.springSecurity.config.security.domain.type.SecurityRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.Getter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JasonWebTokenUtil implements InitializingBean {

    @Value("${spring.security.secret}")
    private String secret;

    @Value("${spring.security.time.access_token}")
    private Long accessTokenValidityTime;

    @Getter
    @Value("${spring.security.time.refresh_token}")
    private Long refreshTokenValidityTime;

    private SecretKey key;

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    public DefaultJsonWebTokenDto generateDefaultJsonWebTokenDto(String identify, SecurityRole role) {
        String accessToken = generateToken(identify,role,accessTokenValidityTime);
        String refreshToken = generateToken(identify,null,refreshTokenValidityTime);
        return new DefaultJsonWebTokenDto(accessToken, refreshToken);
    }

    public Claims validateToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }catch (ExpiredJwtException e) {
            throw new CommonException(EXPIRED_TOKEN_ERROR);
        } catch (JwtException e) {
            throw new CommonException(INVALID_TOKEN_ERROR);
        } catch (IllegalArgumentException e) {
            throw new CommonException(TOKEN_UNSUPPORTED_ERROR);
        }
    }


    private String generateToken(String identify, SecurityRole role, Long expiredPeriod) {
        Claims claims = Jwts.claims().build();

        claims.put(JwtConstants.ACCOUNT_ID_ATTRIBUTE_NAME, identify);
        if(role != null) claims.put(JwtConstants.ACCOUNT_ROLE_CLAIM_NAME, role.name());

        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredPeriod))
                .signWith(key)
                .compact();
    }

}
