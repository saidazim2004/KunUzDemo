package com.example.kunuzdemo.config.security;

import com.example.kunuzdemo.config.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(AUTH_WHITELIST)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    private static final String[] AUTH_WHITELIST = {
            "api/v1/auth/sign-up",
            "api/v1/auth/verify",
            "api/v1/auth/new-verification-code",
            "api/v1/auth/sign-in",
            "api/v1/auth/forgot-password",
            "api/v1/auth/reset-password",
            "api/v1/auth/update-password",
            "api/v1/article/get-by-id",
            "api/v1/article/get-by-language",
            "api/v1/article/get-by-title",
            "api/v1/region/get-by-id",
            "api/v1/region/getAll",
            "/v3/api-docs/**",
            "/v3/api-docs.yml",
            "/swagger-ui/**",
            "swagger-ui.html"
    };
}
