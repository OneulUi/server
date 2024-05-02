package com.swyg.oneului.controller.doc;

import com.swyg.oneului.common.CommonApiResponse;
import com.swyg.oneului.dto.MemberDTO;
import com.swyg.oneului.dto.SurveyDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "회원", description = "회원 API")
public interface MemberControllerDoc {
    @Operation(summary = "회원 설문 정보 저장 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 인자 값")})
    @PostMapping("/member/survey")
    ResponseEntity<CommonApiResponse<?>> updateMemberSurveyByLoginId(
            @Parameter(description = "인증 객체", required = true) Authentication authentication,
            @Parameter(description = "설문 정보 DTO", required = true) @RequestBody SurveyDTO surveyDTO);

    @Operation(summary = "회원 정보와 설문 정보 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 인자 값")})
    @GetMapping("/member/survey")
    ResponseEntity<CommonApiResponse<MemberDTO>> getMemberAndSurveyByLoginId(
            @Parameter(description = "인증 객체", required = true) Authentication authentication);

    @Operation(summary = "회원 정보 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 인자 값")})
    @GetMapping("/current")
    ResponseEntity<CommonApiResponse<MemberDTO>> getMemberByLoginId(
            @Parameter(description = "인증 객체", required = true) Authentication authentication);

    @Operation(summary = "회원 정보 수정 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 인자 값")})
    @PostMapping("/update")
    ResponseEntity<CommonApiResponse<?>> updateMemberProfileByLoginId(
            @Parameter(description = "인증 객체", required = true) Authentication authentication,
            @Parameter(description = "회원 수정 관련 정보 DTO", required = true) @RequestBody MemberDTO memberDTO);
}