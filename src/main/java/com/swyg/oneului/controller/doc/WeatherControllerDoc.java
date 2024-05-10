package com.swyg.oneului.controller.doc;

import com.swyg.oneului.common.CommonApiResponse;
import com.swyg.oneului.dto.WeatherDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Tag(name = "날씨", description = "날씨 API")
public interface WeatherControllerDoc {
    @Operation(summary = "현재 시간에 해당하는 날씨 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 인자 값")})
    @GetMapping("/weather/current")
    ResponseEntity<CommonApiResponse<List<WeatherDTO.Response>>> getCurrentWeathers(
            @Parameter(description = "날짜", required = true, example = "20240505") @RequestParam String baseDate,
            @Parameter(description = "주소", required = true, example = "서울특별시 강서구") @RequestParam String address) throws IOException;

    @Operation(summary = "당일 날씨 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 인자 값")})
    @GetMapping("/weather/hourly")
    ResponseEntity<CommonApiResponse<List<WeatherDTO.Response>>> getHourlyWeathers(
            @Parameter(description = "날짜", required = true, example = "20240505") @RequestParam String baseDate,
            @Parameter(description = "주소", required = true, example = "서울특별시 강서구") @RequestParam String address) throws IOException;
}