package com.swyg.oneului.dto;

import com.swyg.oneului.model.Weather;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class WeatherDTO {
    private String category;
    private String fcstDate;
    private String fcstTime;
    private String fcstValue;
    private String convertCategory;
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
