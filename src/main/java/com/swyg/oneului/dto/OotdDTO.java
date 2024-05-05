package com.swyg.oneului.dto;

import com.swyg.oneului.model.Ootd;
import com.swyg.oneului.model.OotdImage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class OotdDTO {
    private Long ootdId;
    private String review;
    private String temperature;
    private String humidity;
    private String satisfaction;
    private List<OotdImageDTO> ootdImages;

    @Builder
    public OotdDTO(Long ootdId, String review, String temperature, String humidity, String satisfaction, List<OotdImageDTO> ootdImages) {
        this.ootdId = ootdId;
        this.review = review;
        this.temperature = temperature;
        this.humidity = humidity;
        this.satisfaction = satisfaction;
        this.ootdImages = ootdImages;
    }

    public static OotdDTO of(Ootd ootd) {
        return OotdDTO.builder()
                .ootdId(ootd.getOotdId())
                .review(ootd.getReview())
                .temperature(ootd.getTemperature())
                .humidity(ootd.getHumidity())
                .satisfaction(ootd.getSatisfaction())
                .ootdImages(OotdImageDTO.listOf(ootd.getOotdImages()))
                .build();
    }

    public static List<OotdDTO> listOf(List<Ootd> ootds) {
        List<OotdDTO> ootdDTOS = new ArrayList<>();
        for (Ootd ootd : ootds) {
            ootdDTOS.add(OotdDTO.of(ootd));
        }
        return ootdDTOS;
    }
}