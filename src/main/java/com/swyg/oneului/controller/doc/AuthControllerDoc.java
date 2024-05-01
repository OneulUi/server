package com.swyg.oneului.controller.doc;

import com.swyg.oneului.common.CommonApiResponse;
import com.swyg.oneului.dto.MemberDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "인증", description = "인증 API")
public interface AuthControllerDoc {
    @Operation(summary = "구글 로그인 후 리다이렉트 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 인자 값")})
    @GetMapping("/auth/success")
    public ResponseEntity<CommonApiResponse<MemberDTO>> getSuccessResponseWithAccessToken(
            @Parameter(description = "인증 토큰", required = true) @RequestParam(name = "accessToken") String accessToken,
            @Parameter(description = "로그인된 아이디", required = true) @RequestParam(name = "loginId") String loginId);

    @GetMapping("/auth/after-login")
    public String getTextAfterLogin(Authentication authentication);
}