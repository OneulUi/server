package com.swyg.oneului.controller;

import com.swyg.oneului.common.ApiResponse;
import com.swyg.oneului.dto.WeatherDTO;
import com.swyg.oneului.model.AddressPosition;
import com.swyg.oneului.model.Weather;
import com.swyg.oneului.service.WeatherService;
import com.swyg.oneului.util.ExcelCacheLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/weather")
@RestController
public class WeatherController {
    private final WeatherService weatherService;
    private final ExcelCacheLoader excelCacheLoader;

    @GetMapping("/current")
    public ResponseEntity<ApiResponse<List<WeatherDTO>>> getCurrentWeather(@RequestParam String baseDate, @RequestParam String address) throws IOException {
        AddressPosition addressPosition = excelCacheLoader.getPositionFromAddressCache(address);
        List<Weather.Item> currentWeather = weatherService.getCurrentWeather(baseDate, addressPosition.getNx(), addressPosition.getNy());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.createSuccess(WeatherDTO.listOf(currentWeather)));
    }

    @GetMapping("/hourly")
    public ResponseEntity<ApiResponse<List<WeatherDTO>>> getHourlyWeather(@RequestParam String baseDate, @RequestParam String address) throws IOException {
        AddressPosition addressPosition = excelCacheLoader.getPositionFromAddressCache(address);
        List<Weather.Item> hourlyWeathers = weatherService.getHourlyWeather(baseDate, addressPosition.getNx(), addressPosition.getNy());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.createSuccess(WeatherDTO.listOf(hourlyWeathers)));
    }
}