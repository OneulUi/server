package com.swyg.oneului.controller.doc;

import com.swyg.oneului.common.CommonApiResponse;
import com.swyg.oneului.dto.SurveyDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Tag(name = "설문정보", description = "설문정보 API")
public interface SurveyControllerDoc {
    @Operation(summary = "모든 설문정보를 조회하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공")})
    @GetMapping("/survey/options")
    ResponseEntity<CommonApiResponse<List<SurveyDTO.Response>>> getAllSurveys();
}