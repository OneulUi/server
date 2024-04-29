package com.swyg.oneului.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.swyg.oneului.util.WeatherDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class Weather {
    @JsonDeserialize(using = WeatherDeserializer.class)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Items {
        private List<Item> items;
    }

    @NoArgsConstructor
    @Getter
    public static class Item {
        private String baseDate;
        private String baseTime;
        private String category;
        private String fcstDate;
        private String fcstTime;
        private String fcstValue;
        private Long nx;
        private Long ny;
        private String convertCategory;
        private String convertFcstValue;

        public void initConvertCategory(String convertCategory) {
            this.convertCategory = convertCategory;
        }

        public void initConvertFcstValue(String convertFcstValue) {
            this.convertFcstValue = convertFcstValue;
        }
    }
}
