package com.swyg.oneului.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class WeatherDictionary {
    static Map<String, String> dictionary = new HashMap<>();
    static Map<String, String> skyDictionary = new HashMap<>();
    static Map<String, String> ptyDictionary = new HashMap<>();

    public WeatherDictionary() {
        dictionary.put("POP", "강수확률");
        dictionary.put("TMN", "최저기온");
        dictionary.put("TMX", "최고기온");
        dictionary.put("TMP", "현재기온");
        dictionary.put("SKY", "하늘상태");
        dictionary.put("PTY", "강수형태");

        skyDictionary.put("1", "맑음");
        skyDictionary.put("3", "구름많음");
        skyDictionary.put("4", "흐림");

        ptyDictionary.put("0", "없음");
        ptyDictionary.put("1", "비");
        ptyDictionary.put("2", "비/눈");
        ptyDictionary.put("3", "눈");
        ptyDictionary.put("4", "소나기");
    }

    public static String convertWeatherCode(String weatherCode) {
        return dictionary.get(weatherCode);
    }

    public static String convertWeatherValue(String weatherCode, String weatherValue) {
        if (weatherCode.equals("SKY")) {
            return skyDictionary.get(weatherValue);
        }

        if (weatherCode.equals("PTY")) {
            return ptyDictionary.get(weatherValue);
        }

        return weatherValue;
    }

    public static boolean isRelatedWeatherCode(String weatherCode) {
        return weatherCode.equals("TMP") || weatherCode.equals("TMN") || weatherCode.equals("TMX") || weatherCode.equals("PTY") || weatherCode.equals("SKY") || weatherCode.equals("POP");
    }
}
