package com.swyg.oneului.controller;

import com.swyg.oneului.common.CommonApiResponse;
import com.swyg.oneului.controller.doc.WeatherControllerDoc;
import com.swyg.oneului.dto.WeatherDTO;
import com.swyg.oneului.model.AddressPosition;
import com.swyg.oneului.model.Weather;
import com.swyg.oneului.service.WeatherService;
import com.swyg.oneului.util.ExcelCacheLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class WeatherController implements WeatherControllerDoc {
    private final WeatherService weatherService;
    private final ExcelCacheLoader excelCacheLoader;

    @GetMapping("/weather/current")
    public ResponseEntity<CommonApiResponse<List<WeatherDTO.Response>>> getCurrentWeathers(
            @RequestParam String baseDate,
            @RequestParam String address) throws IOException {

        AddressPosition addressPosition = excelCacheLoader.getPositionFromAddressCache(address);
        List<Weather.Item> currentWeather = weatherService.getCurrentWeather(baseDate, addressPosition.getNx(), addressPosition.getNy());

        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.createSuccess(WeatherDTO.Response.listOf(currentWeather)));
    }

    @GetMapping("/weather/hourly")
    public ResponseEntity<CommonApiResponse<List<WeatherDTO.Response>>> getHourlyWeathers(
            @RequestParam String baseDate,
            @RequestParam String address) throws IOException {

        AddressPosition addressPosition = excelCacheLoader.getPositionFromAddressCache(address);
        List<Weather.Item> hourlyWeathers = weatherService.getHourlyWeather(baseDate, addressPosition.getNx(), addressPosition.getNy());

        return ResponseEntity.status(HttpStatus.OK).body(CommonApiResponse.createSuccess(WeatherDTO.Response.listOf(hourlyWeathers)));
    }
}