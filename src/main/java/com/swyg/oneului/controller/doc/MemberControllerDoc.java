package com.swyg.oneului.controller.doc;

import com.swyg.oneului.dto.SurveyDTO;
import com.swyg.oneului.model.Survey;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface MemberControllerDoc {
    @Operation(summary = "member survey 수정 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 인자 값")})
    @PostMapping("/member/survey")
    void updateMemberSurvey(
            @Parameter(description = "인증 객체", required = true) Authentication authentication,
            @Parameter(description = "설문 정보 DTO", required = true) @RequestBody SurveyDTO surveyDTO);
}
