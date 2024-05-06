package com.swyg.oneului.config;

import com.swyg.oneului.security.JwtAuthenticationEntryPoint;
import com.swyg.oneului.security.TokenAuthenticationFilter;
import com.swyg.oneului.security.oauth2.OAuth2AuthorizationRequestRepository;
import com.swyg.oneului.security.oauth2.OAuth2SuccessHandler;
import com.swyg.oneului.service.OAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final OAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2AuthorizationRequestRepository oAuth2AuthorizationRequestRepository;
    private final TokenAuthenticationFilter tokenAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> {
            web.ignoring()
                    .requestMatchers("/error",
                            "/favicon.ico",
                            "/error",
                            "/swagger-ui/**",
                            "/swagger-ui.*",
                            "/swagger-resources/**",
                            "/v3/api-docs/**");
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .headers(headersConfigurer -> {
                    headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin);
                })
                .sessionManagement(sessionManagementConfigurer -> {
                    sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .authorizeHttpRequests(matcherRegistry -> {
                    matcherRegistry
                            .requestMatchers("/", "/hello", "/auth/success", "/weather/**").permitAll()
                            .requestMatchers(PathRequest.toH2Console()).permitAll()
                            .anyRequest().authenticated();
                })
                .oauth2Login(oAuth2LoginConfigurer -> {
                    oAuth2LoginConfigurer
                            .authorizationEndpoint(endpointConfig -> {
                                endpointConfig.authorizationRequestRepository(oAuth2AuthorizationRequestRepository);
                            })
                            .userInfoEndpoint(userInfoEndpointConfig -> {
                                userInfoEndpointConfig.userService(oAuth2UserService);
                            })
                            .successHandler(oAuth2SuccessHandler);
                })
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandlingConfigurer -> {
                    exceptionHandlingConfigurer
                            .authenticationEntryPoint(jwtAuthenticationEntryPoint);
                });

        return http.build();
    }

    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}