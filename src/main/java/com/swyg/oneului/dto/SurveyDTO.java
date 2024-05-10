package com.swyg.oneului.dto;

import com.swyg.oneului.model.Survey;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class SurveyDTO {
    @Setter
    @Getter
    public static class Request {
        @Schema(description = "설문정보 고유 번호")
        private Long surveyId;

        public Request() {
        }

        @Builder
        public Request(Long surveyId) {
            this.surveyId = surveyId;
        }

        public static Survey toEntity(SurveyDTO.Request surveyDTO) {
            return Survey.builder()
                    .surveyId(surveyDTO.getSurveyId())
                    .build();
        }
    }

    @Setter
    @Getter
    public static class Response {
        @Schema(description = "저장된 설문정보 고유 번호")
        private Long surveyId;

        @Schema(description = "저장된 설문정보 항목 내용")
        private String options;

        @Schema(description = "저장된 설문정보의 가중치")
        private int weights;

        public Response() {
        }

        @Builder
        public Response(Long surveyId, String options, int weights) {
            this.surveyId = surveyId;
            this.options = options;
            this.weights = weights;
        }

        public static SurveyDTO.Response of(Survey survey) {
            return Response.builder()
                    .surveyId(survey.getSurveyId())
                    .options(survey.getOptions())
                    .weights(survey.getWeights())
                    .build();
        }

        public static List<SurveyDTO.Response> listOf(List<Survey> surveys) {
            List<SurveyDTO.Response> responses = new ArrayList<>();
            for (Survey survey : surveys) {
                responses.add(SurveyDTO.Response.of(survey));
            }

            return responses;
        }
    }
}