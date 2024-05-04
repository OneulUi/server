package com.swyg.oneului.controller;

import com.swyg.oneului.common.CommonApiResponse;
import com.swyg.oneului.controller.doc.SurveyControllerDoc;
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
public class SurveyController implements SurveyControllerDoc {
    private final SurveyService surveyService;

    @GetMapping("/options")
    public ResponseEntity<CommonApiResponse<List<SurveyDTO>>> getAllSurveys() {
//        List<Survey> surveys = surveyService.findAllSurveys();
        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.createSuccess(SurveyDTO.listOf(null)));
    }
}