package com.swyg.oneului.controller;

import com.swyg.oneului.common.ApiResponse;
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
@RequestMapping("/member")
@RestController
public class MemberController implements MemberControllerDoc {
    private final MemberService memberService;
    private final SurveyService surveyService;

    @PostMapping("/survey")
    public void updateMemberSurvey(Authentication authentication, @RequestBody SurveyDTO surveyDTO) {
        String loginId = authentication.getName();
        Survey survey = surveyService.findSurveyBySurveyId(surveyDTO.getSurveyId());

        memberService.updateMemberSurvey(loginId, survey);
    }

    @GetMapping("/survey")
    public ResponseEntity<ApiResponse<MemberDTO>> getMemberAndSurveyByLoginId(Authentication authentication) {
        String loginId = authentication.getName();
        Member member = memberService.findMemberAndSurveyByLoginId(loginId);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.createSuccess(MemberDTO.of(member)));
    }
}