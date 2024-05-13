package com.swyg.oneului.controller;

import com.swyg.oneului.common.CommonApiResponse;
import com.swyg.oneului.controller.doc.MemberControllerDoc;
import com.swyg.oneului.dto.MemberDTO;
import com.swyg.oneului.dto.SurveyDTO;
import com.swyg.oneului.model.Member;
import com.swyg.oneului.model.Survey;
import com.swyg.oneului.service.MemberService;
import com.swyg.oneului.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MemberController implements MemberControllerDoc {
    private final MemberService memberService;
    private final SurveyService surveyService;

    @GetMapping("/member")
    public ResponseEntity<CommonApiResponse<MemberDTO.Response>> getMemberProfileByLoginId(
            Authentication authentication) {

        String loginId = authentication.getName();
        Member member = memberService.findMemberByLoginId(loginId);

        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.createSuccess(MemberDTO.Response.of(member)));
    }

    @PutMapping("/member")
    public ResponseEntity<CommonApiResponse<?>> updateMemberProfileByLoginId(
            Authentication authentication,
            @RequestBody MemberDTO.Update memberDTO) {

        String loginId = authentication.getName();
        memberService.updateMemberProfile(loginId, MemberDTO.Update.toEntity(memberDTO));

        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.createSuccessWithNoContent());
    }

    @PostMapping("/member/survey")
    public ResponseEntity<CommonApiResponse<?>> updateMemberSurveyByLoginId(
            Authentication authentication,
            @RequestBody SurveyDTO.Request surveyDTO) {

        String loginId = authentication.getName();
        Survey survey = surveyService.findSurveyBySurveyId(surveyDTO.getSurveyId());

        memberService.updateMemberSurvey(loginId, survey);

        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.createSuccessWithNoContent());
    }
}