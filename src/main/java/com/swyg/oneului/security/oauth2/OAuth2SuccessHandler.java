package com.swyg.oneului.security.oauth2;

import com.swyg.oneului.security.TokenProvider;
import com.swyg.oneului.util.CookieUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
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
import java.util.Optional;

import static com.swyg.oneului.security.oauth2.OAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final TokenProvider tokenProvider;
    private final OAuth2AuthorizationRequestRepository oAuth2AuthorizationRequestRepository;
    private static final String DEFAULT_BASE_URL = "http://localhost:3000/login/redirect";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // AccessToken, RefreshToken 발급
        String accessToken = tokenProvider.generateAccessToken(authentication);
        String loginId = authentication.getName();
        tokenProvider.generateRefreshToken(authentication);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("accessToken", accessToken);
        params.add("loginId", loginId);

        String targetUrl = determineTargetUrl(request, response, authentication);

        // 토큰 전달을 위한 리다이렉트
        String redirectUrl = ServletUriComponentsBuilder
                .fromUriString(targetUrl)
                .queryParams(params)
                .build()
                .toUriString();

        response.sendRedirect(redirectUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME).map(Cookie::getValue);
        clearAuthenticationAttributes(request, response);
        return redirectUri.orElse(DEFAULT_BASE_URL);
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        oAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}