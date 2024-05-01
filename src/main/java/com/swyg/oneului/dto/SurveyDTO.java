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
    @Schema(description = "설문지 PK", defaultValue = "1L")
    private Long surveyId;

    @Schema(description = "설문지 옵션", defaultValue = "지훈님 option이 뭔지 모르겠어요.. ㅋㅋ")
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
