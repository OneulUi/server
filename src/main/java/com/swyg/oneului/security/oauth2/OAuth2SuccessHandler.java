package com.swyg.oneului.security.oauth2;

import com.swyg.oneului.security.TokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.MultiMap;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final TokenProvider tokenProvider;
    private static final String SUCCESS_REDIRECT_URI = "/auth/success";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // AccessToken, RefreshToken 발급
        String accessToken = tokenProvider.generateAccessToken(authentication);
        String loginId = authentication.getName();
        tokenProvider.generateRefreshToken(authentication);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("accessToken", accessToken);
        params.add("loginId", loginId);

        // 토큰 전달을 위한 리다이렉트
        String redirectUrl = ServletUriComponentsBuilder
                .fromUriString(SUCCESS_REDIRECT_URI)
                .queryParams(params)
                .build()
                .toUriString();

        response.sendRedirect(redirectUrl);
    }
}