package com.swyg.oneului.controller;

import com.swyg.oneului.common.ApiResponse;
import com.swyg.oneului.dto.SurveyDTO;
import com.swyg.oneului.model.Survey;
import com.swyg.oneului.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/survey")
@RestController
public class SurveyController {
    private final SurveyService surveyService;

    @GetMapping("/options")
    public ResponseEntity<ApiResponse<List<SurveyDTO>>> getAllSurveys() {
        List<Survey> surveys = surveyService.findAllSurveys();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.createSuccess(SurveyDTO.listOf(surveys)));
    }
}