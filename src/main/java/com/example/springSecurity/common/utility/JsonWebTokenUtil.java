package com.example.springSecurity.common.utility;

import static com.example.springSecurity.common.exception.ErrorCode.EXPIRED_TOKEN_ERROR;
import static com.example.springSecurity.common.exception.ErrorCode.INVALID_TOKEN_ERROR;
import static com.example.springSecurity.common.exception.ErrorCode.TOKEN_UNSUPPORTED_ERROR;

import com.example.springSecurity.common.constant.Constants;
import com.example.springSecurity.common.exception.CommonException;
import com.example.springSecurity.security.app.dto.DefaultJsonWebTokenDto;
import com.example.springSecurity.security.domain.type.SecurityRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JsonWebTokenUtil implements InitializingBean {

    @Value("${spring.security.secret}")
    private String secret;

    @Value("${spring.security.time.access_token}")
    private Long accessTokenValidityTime;

    private SecretKey key;

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    public DefaultJsonWebTokenDto generateDefaultJsonWebTokenDto(String identify, String role) {
        String accessToken = generateToken(identify,role,accessTokenValidityTime);
        return new DefaultJsonWebTokenDto(accessToken);
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


    private String generateToken(String identify, String role, Long expiredPeriod) {
        Claims claims = Jwts.claims()
                .add(Constants.ACCOUNT_ID_ATTRIBUTE_NAME, identify)
                .add(Constants.ACCOUNT_ROLE_CLAIM_NAME, role)
                .build();

        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredPeriod))
                .signWith(key)
                .compact();
    }

}
