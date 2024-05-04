package com.swyg.oneului.dto;

import com.swyg.oneului.model.Survey;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class SurveyDTO {
    @Schema(description = "설문 정보 PK", defaultValue = "1")
    private Long surveyId;

    @Schema(description = "설문 항목")
    private String options;

    public SurveyDTO() {
    }

    @Builder
    public SurveyDTO(Long surveyId, String options) {
        this.surveyId = surveyId;
        this.options = options;
    }

    public static SurveyDTO of(Survey survey) {
        return SurveyDTO.builder()
                .surveyId(survey.getSurveyId())
                .options(survey.getOptions())
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