package com.example.springSecurity.security.config;


import com.example.springSecurity.common.handler.DefaultAccessDeniedHandler;
import com.example.springSecurity.common.handler.DefaultAuthenticationEntryPoint;
import com.example.springSecurity.common.handler.DefaultLoginFailureHandler;
import com.example.springSecurity.common.handler.DefaultLoginSuccessHandler;
import com.example.springSecurity.common.handler.DefaultLogoutProcessHandler;
import com.example.springSecurity.common.handler.DefaultLogoutSuccessHandler;
import com.example.springSecurity.common.constant.Constants;
import com.example.springSecurity.common.utility.JsonWebTokenUtil;
import com.example.springSecurity.security.info.CustomUserDetailService;
import com.example.springSecurity.security.filter.JsonWebTokenAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsConfig corsConfig;

    private final DefaultLoginSuccessHandler defaultLoginSuccessHandler;
    private final DefaultLoginFailureHandler defaultLoginFailureHandler;

    private final DefaultLogoutProcessHandler defaultLogoutProcessHandler;
    private final DefaultLogoutSuccessHandler defaultLogoutSuccessHandler;

    private final DefaultAccessDeniedHandler defaultAccessDeniedHandler;
    private final DefaultAuthenticationEntryPoint defaultAuthenticationEntryPoint;

    private final JsonWebTokenUtil jsonWebTokenUtil;

    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //csrf 설정 - jwt를 사용할 때는 비활성화
                .csrf(AbstractHttpConfigurer::disable)

                .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))

                //기본 Base64 인코딩으로만 암호화하는 것, 보안성이 좋지 않다. jwt를 사용하고 있으니 비활성화해준다.
                .httpBasic(AbstractHttpConfigurer::disable)

                //session 로그인 관련된 로직이다. -> jwt를 사용하여 별도의 세션을 저장하지 않으므로 비활성화
                .sessionManagement(configurer -> configurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                //인증 및 인가에 관헌 설정
                .authorizeHttpRequests(configurer -> configurer
                        //해당 URL 들에 대해서는 인증을 수행하지 않는다.
                        .requestMatchers(Constants.NO_NEED_AUTH_URLS.toArray(String[]::new)).permitAll()
                        //해당 URL들에 대해서는 인가를 적용한다. hasAnyRole 의 매개변수에 인가 적용 대상 기입
                        .requestMatchers(Constants.USER_URLS.toArray(String[]::new)).hasAnyRole("USER","OWNER")
//                        .requestMatchers(Constants.USER_URLS.toArray(String[]::new)).hasAnyAuthority("USER","OWNER") DB에 Role과 정확이 이름이 같아야한다.
                        .anyRequest().authenticated())


                .formLogin(configurer -> configurer
                        .loginPage("/login")
                        .loginProcessingUrl("/api/auth/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler(defaultLoginSuccessHandler)
                        .failureHandler(defaultLoginFailureHandler)
                        )

                .logout(configurer -> configurer
                        .logoutUrl("/api/auth/logout")
                        .addLogoutHandler(defaultLogoutProcessHandler)
                        .logoutSuccessHandler(defaultLogoutSuccessHandler))

                .exceptionHandling(configurer -> configurer
                        .accessDeniedHandler(defaultAccessDeniedHandler)
                        .authenticationEntryPoint(defaultAuthenticationEntryPoint))

                .addFilterBefore(
                        new JsonWebTokenAuthenticationFilter(
                                jsonWebTokenUtil,
                                userDetailsService
                        ), LogoutFilter.class
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}

}