package com.swyg.oneului.controller.advice;

import com.swyg.oneului.common.CommonApiResponse;
import com.swyg.oneului.exception.TokenException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionResponseHandler {
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<CommonApiResponse<?>> handleSignatureException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonApiResponse.createError("토큰이 유효하지 않습니다."));
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<CommonApiResponse<?>> handleMalformedJwtException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonApiResponse.createError("올바르지 않은 토큰입니다."));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<CommonApiResponse<?>> handleExpiredJwtException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonApiResponse.createError("토큰이 만료되었습니다. 다시 로그인해주세요."));
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<CommonApiResponse<?>> handleTokenException(TokenException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonApiResponse.createError(e.getMessage()));
    }
}