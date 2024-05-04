package com.swyg.oneului.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swyg.oneului.model.Weather;
import com.swyg.oneului.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class WeatherService {
    private final ObjectMapper objectMapper;
    @Value("${weather.key}")
    private String WEATHER_API_KEY;
    private static final String BASE_URL = "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";
    private static final String PAGE_NO = "1";
    private static final String NUM_OF_ROWS = "1000";
    private static final String DATA_TYPE = "JSON";
    private static final String BASE_TIME = "2300";

    public List<Weather.Item> getCurrentWeather(String baseDate, String nx, String ny) throws IOException {
        List<Weather.Item> currentWeather = new ArrayList<>();

        List<Weather.Item> weatherData = getWeatherData(nx, ny);
        Weather.Item minTemperature = weatherData.stream()
                .filter(item -> item.getFcstDate().equals(baseDate))
                .filter(item -> item.getCategory().equals("TMN"))
                .toList()
                .get(0);

        Weather.Item maxTemperature = weatherData.stream()
                .filter(item -> item.getFcstDate().equals(baseDate))
                .filter(item -> item.getCategory().equals("TMX"))
                .toList()
                .get(0);

        List<Weather.Item> currentTemperature = weatherData.stream()
                .filter(item -> item.getFcstDate().equals(baseDate))
                .filter(item -> item.getCategory().equals("TMP") ||
                        item.getCategory().equals("SKY") ||
                        item.getCategory().equals("PTY") ||
                        item.getCategory().equals("POP"))
                .filter(item -> item.getFcstTime().equals(DateUtils.getFormattedNowDate()))
                .toList();

        currentWeather.add(minTemperature);
        currentWeather.add(maxTemperature);
        currentWeather.addAll(currentTemperature);

        return currentWeather;
    }

    public List<Weather.Item> getHourlyWeather(String baseDate, String nx, String ny) throws IOException {
        List<Weather.Item> weatherData = getWeatherData(nx, ny);

        return weatherData.stream()
                .filter(item -> item.getFcstDate().equals(baseDate))
                .filter(item -> item.getCategory().equals("TMP") ||
                        item.getCategory().equals("SKY") ||
                        item.getCategory().equals("PTY") ||
                        item.getCategory().equals("POP"))
                .toList();
    }

    private List<Weather.Item> getWeatherData(String nx, String ny) throws IOException {
        String weatherApiUrl = buildWeatherApiUrl(nx, ny);
        URL url = new URL(weatherApiUrl);

        // Connection 생성 후 GET메서드 호출
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-type", "application/json");

        // 응답 상태 코드에 따라 분기 처리
        BufferedReader bufferedReader;
        if (connection.getResponseCode() >= 200 && connection.getResponseCode() <= 300) {
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        }

        // 버퍼에 저장된 값 문자열로 변환
        StringBuilder answer = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            answer.append(line);
        }

        // 연결 닫기
        bufferedReader.close();
        connection.disconnect();

        Weather.Items items = objectMapper.readValue(answer.toString(), Weather.Items.class);
        return items.getItems();
    }

    private String buildWeatherApiUrl(String nx, String ny) {
        return BASE_URL + "?" + URLEncoder.encode("serviceKey", StandardCharsets.UTF_8) + "=" + WEATHER_API_KEY +
                "&" + URLEncoder.encode("pageNo", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(PAGE_NO, StandardCharsets.UTF_8) +
                "&" + URLEncoder.encode("numOfRows", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(NUM_OF_ROWS, StandardCharsets.UTF_8) +
                "&" + URLEncoder.encode("dataType", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(DATA_TYPE, StandardCharsets.UTF_8) +
                "&" + URLEncoder.encode("base_date", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(DateUtils.getPreviousTwoDate(), StandardCharsets.UTF_8) +
                "&" + URLEncoder.encode("base_time", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(BASE_TIME, StandardCharsets.UTF_8) +
                "&" + URLEncoder.encode("nx", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(nx, StandardCharsets.UTF_8) +
                "&" + URLEncoder.encode("ny", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(ny, StandardCharsets.UTF_8);
    }
}
