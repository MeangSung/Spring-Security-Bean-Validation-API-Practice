package com.example.springSecurity.security.filter;

import com.example.springSecurity.common.constant.Constants;
import com.example.springSecurity.common.exception.CommonException;
import com.example.springSecurity.common.exception.ErrorCode;
import com.example.springSecurity.common.utility.HeaderUtil;
import com.example.springSecurity.common.utility.JsonWebTokenUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Slf4j
public class JsonWebTokenAuthenticationFilter extends OncePerRequestFilter {

    private final JsonWebTokenUtil jsonWebTokenUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String token = HeaderUtil.refineHeader(request, Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_HEADER_ERROR));

        Claims claims = jsonWebTokenUtil.validateToken(token);

        String email = claims.get(Constants.ACCOUNT_ID_ATTRIBUTE_NAME, String.class);
        String securityRole = claims.get(Constants.ACCOUNT_ROLE_CLAIM_NAME, String.class);

//        if(securityRole == null){
//            throw new CommonException(ErrorCode.ACCESS_DENIED);
//        }

        UserDetails principal = userDetailsService.loadUserByUsername(email);

        //AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                principal,
                null,
                List.of(new SimpleGrantedAuthority(securityRole))
        );

        //SecurityContext 에 AuthenticationToken 저장
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

//        SecurityContext context = SecurityContextHolder.createEmptyContext();
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authenticationToken);
        SecurityContextHolder.setContext(context);

        //다음 필터로 전달
        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String requestURI = request.getRequestURI();

        // 인증이 필요 없는 URL 목록에 포함되는지 확인
        return Constants.NO_NEED_AUTH_URLS.stream()
                .anyMatch(excludePattern -> requestURI.matches(excludePattern.replace("**", ".*")));
    }

}

