package com.swyg.oneului.dto;

import com.swyg.oneului.model.Weather;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class WeatherDTO {
    @Schema(description = "날씨 항목 카테고리 코드")
    private String category;
    @Schema(description = "예측 일자")
    private String fcstDate;
    @Schema(description = "예측 시간")
    private String fcstTime;
    @Schema(description = "예측 값 코드")
    private String fcstValue;
    @Schema(description = "날씨 항목 카테고리 값")
    private String convertCategory;
    @Schema(description = "예측 값")
    private String convertFcstValue;

    @Builder
    public WeatherDTO(String category, String fcstDate, String fcstTime, String fcstValue, String convertCategory, String convertFcstValue) {
        this.category = category;
        this.fcstDate = fcstDate;
        this.fcstTime = fcstTime;
        this.fcstValue = fcstValue;
        this.convertCategory = convertCategory;
        this.convertFcstValue = convertFcstValue;
    }

    public static WeatherDTO of(Weather.Item item) {
        return WeatherDTO.builder()
                .category(item.getCategory())
                .fcstDate(item.getFcstDate())
                .fcstTime(item.getFcstTime())
                .fcstValue(item.getFcstValue())
                .convertCategory(item.getConvertCategory())
                .convertFcstValue(item.getConvertFcstValue())
                .build();
    }

    public static List<WeatherDTO> listOf(List<Weather.Item> items) {
        List<WeatherDTO> weatherDTOS = new ArrayList<>();
        for (Weather.Item item : items) {
            weatherDTOS.add(WeatherDTO.of(item));
        }
        return weatherDTOS;
    }
}
