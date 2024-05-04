package com.swyg.oneului.service;

import com.swyg.oneului.model.Survey;
import com.swyg.oneului.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SurveyService {
    private final SurveyRepository surveyRepository;

    @Transactional
    public void initSurveyOptions() {
        List<Survey> surveys = Survey.initSurveyOptions();
        surveyRepository.saveAll(surveys);
    }

    public Survey findSurveyBySurveyId(Long surveyId) {
        Optional<Survey> survey = surveyRepository.findSurveyBySurveyId(surveyId);
        return survey.orElse(null);
    }

    public List<Survey> findAllSurveys() {
        return surveyRepository.findAll();
    }
}
