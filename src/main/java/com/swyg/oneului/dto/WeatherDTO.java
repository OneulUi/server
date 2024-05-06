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
    @Setter
    @Getter
    public static class Request {

    }

    @Setter
    @Getter
    public static class Response {
        @Schema(description = "날씨 카테고리 코드")
        private String category;

        @Schema(description = "예측 일자")
        private String fcstDate;

        @Schema(description = "예측 시간")
        private String fcstTime;

        @Schema(description = "예측 코드")
        private String fcstValue;

        @Schema(description = "날씨 카테고리 값")
        private String convertCategory;

        @Schema(description = "예측 값")
        private String convertFcstValue;

        public Response() {
        }

        @Builder
        public Response(String category, String fcstDate, String fcstTime, String fcstValue, String convertCategory, String convertFcstValue) {
            this.category = category;
            this.fcstDate = fcstDate;
            this.fcstTime = fcstTime;
            this.fcstValue = fcstValue;
            this.convertCategory = convertCategory;
            this.convertFcstValue = convertFcstValue;
        }

        public static WeatherDTO.Response of(Weather.Item weather) {
            return WeatherDTO.Response.builder()
                    .category(weather.getCategory())
                    .fcstDate(weather.getFcstDate())
                    .fcstTime(weather.getFcstTime())
                    .fcstValue(weather.getFcstValue())
                    .convertCategory(weather.getConvertCategory())
                    .convertFcstValue(weather.getConvertFcstValue())
                    .build();
        }

        public static List<WeatherDTO.Response> listOf(List<Weather.Item> weathers) {
            List<WeatherDTO.Response> responses = new ArrayList<>();
            for (Weather.Item weather : weathers) {
                responses.add(WeatherDTO.Response.of(weather));
            }

            return responses;
        }
    }
}
