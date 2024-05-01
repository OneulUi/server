package com.swyg.oneului.controller;

import com.swyg.oneului.common.CommonApiResponse;
import com.swyg.oneului.controller.doc.AuthControllerDoc;
import com.swyg.oneului.dto.MemberDTO;
import com.swyg.oneului.model.Member;
import com.swyg.oneului.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController implements AuthControllerDoc {
    private final MemberService memberService;

    @GetMapping("/success")
    public ResponseEntity<CommonApiResponse<MemberDTO>> getSuccessResponseWithAccessToken(
            @RequestParam(name = "accessToken") String accessToken,
            @RequestParam(name = "loginId") String loginId) {

        Member loginedMember = memberService.findMemberByLoginId(loginId);
        MemberDTO memberDTO = MemberDTO.of(loginedMember);

        return ResponseEntity.status(HttpStatus.OK).header("accessToken", accessToken).body(CommonApiResponse.createSuccess(memberDTO));
    }

    @GetMapping("/after-login")
    public String getTextAfterLogin(Authentication authentication) {
        return "SUCCESS AUTHENTICATION";
    }
}