package com.swyg.oneului.security;

import com.swyg.oneului.model.TokenHeader;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String accessToken = resolveToken(request);

            // accessToken 검증
            if (tokenProvider.validateToken(accessToken)) {
                setAuthentication(accessToken);
            } else {
                // 만료되었을 경우 accessToken 재발급
                String loginId = tokenProvider.extractSubjectFromToken(accessToken);
                String reissueAccessToken = tokenProvider.reissueAccessToken(loginId);

                if (StringUtils.hasText(reissueAccessToken)) {
                    setAuthentication(reissueAccessToken);
                    response.setHeader(TokenHeader.AUTHORIZATION.getValue(), TokenHeader.TOKEN_PREFIX.getValue() + reissueAccessToken);
                }
            }
        } catch (Exception e) {
            request.setAttribute("exception", e);
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(TokenHeader.AUTHORIZATION.getValue());
        if (ObjectUtils.isEmpty(token) || !token.startsWith(TokenHeader.TOKEN_PREFIX.getValue())) {
            return null;
        }
        return token.substring(TokenHeader.TOKEN_PREFIX.getValue().length());
    }

    private void setAuthentication(String accessToken) {
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}