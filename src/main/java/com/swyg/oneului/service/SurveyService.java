package com.swyg.oneului.service;

import com.swyg.oneului.model.Survey;
import com.swyg.oneului.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SurveyService {
    private final SurveyRepository surveyRepository;

    @Transactional
    public void initSurveyOptions() {
        List<Survey> surveys = Survey.initSurveyOptions();
        List<Survey> originSurveys = surveyRepository.findAll();
        if (originSurveys.isEmpty()) {
            surveyRepository.saveAll(surveys);
        }
    }

    public Survey findSurveyBySurveyId(Long surveyId) {
        return surveyRepository.findSurveyBySurveyId(surveyId)
                .orElseThrow(() -> new NoSuchElementException("해당 설문조사 정보가 존재하지 않습니다."));
    }

    public List<Survey> findAllSurveys() {
        return surveyRepository.findAll();
    }
}
