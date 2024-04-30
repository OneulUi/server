package com.swyg.oneului.dto;

import com.swyg.oneului.model.Survey;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class SurveyDTO {
    private Long surveyId;
    private String option;

    public SurveyDTO() {
    }

    @Builder
    public SurveyDTO(Long surveyId, String option) {
        this.surveyId = surveyId;
        this.option = option;
    }

    public static SurveyDTO of(Survey survey) {
        return SurveyDTO.builder()
                .surveyId(survey.getSurveyId())
                .option(survey.getOption())
                .build();
    }

    public static List<SurveyDTO> listOf(List<Survey> surveys) {
        List<SurveyDTO> surveyDTOS = new ArrayList<>();
        for (Survey survey : surveys) {
            surveyDTOS.add(SurveyDTO.of(survey));
        }
        return surveyDTOS;
    }
}
