package com.swyg.oneului.controller;

import com.swyg.oneului.common.ApiResponse;
import com.swyg.oneului.dto.MemberDTO;
import com.swyg.oneului.model.Member;
import com.swyg.oneului.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {
    private final MemberService memberService;

    @GetMapping("/success")
    public ResponseEntity<ApiResponse<?>> getSuccessResponseWithAccessToken(
            @RequestParam(name = "accessToken") String accessToken,
            @RequestParam(name = "loginId") String loginId) {

        Member loginedMember = memberService.findMemberByLoginId(loginId);
        MemberDTO memberDTO = MemberDTO.of(loginedMember);

        return ResponseEntity.status(HttpStatus.OK).header("accessToken", accessToken).body(ApiResponse.createSuccess(memberDTO));
    }

    @GetMapping("/after-login")
    public String getTextAfterLogin() {
        return "SUCCESS AUTHENTICATION";
    }
}