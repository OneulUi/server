package com.swyg.oneului.util;

import com.swyg.oneului.service.SurveyService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SurveyInitializer {
    private final SurveyService surveyService;

    @PostConstruct
    public void init() throws Exception {
        surveyService.initSurveyOptions();
    }
}
