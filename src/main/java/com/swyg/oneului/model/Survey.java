package com.swyg.oneului.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Survey extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long surveyId;
    private String options;
    private int weights;

    @Builder
    public Survey(Long surveyId, String options, int weights) {
        this.surveyId = surveyId;
        this.options = options;
        this.weights = weights;
    }

    public static List<Survey> initSurveyOptions() {
        int weight = 2;
        String[] options = {
                "평소에 추위를 많이 타는 편이다.",
                "다른 사람들과 비슷한 정도로 추위를 타는 편이다.",
                "추위나 더위를 타지 않는 편이다.",
                "다른 사람들과 비슷한 정도로 더위를 타는 편이다.",
                "평소에 더위를 많이 타는 편이다."
        };

        List<Survey> surveys = new ArrayList<>();
        for (String option : options) {
            Survey survey = Survey.builder()
                    .options(option)
                    .weights(weight--)
                    .build();

            surveys.add(survey);
        }

        return surveys;
    }
}