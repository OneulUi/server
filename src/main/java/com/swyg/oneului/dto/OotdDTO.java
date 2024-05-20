package com.swyg.oneului.dto;

import com.swyg.oneului.model.Ootd;
import com.swyg.oneului.util.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class OotdDTO {
    @Setter
    @Getter
    public static class Create {
        @Schema(description = "OOTD 후기")
        private String review;

        @Schema(description = "OOTD 작성할 때 온도")
        private String temperature;

        @Schema(description = "OOTD 작성할 때 습도")
        private String humidity;

        @Schema(description = "OOTD 작성할 때 만족도", example = "Y 또는 N으로 값을 입력해주세요.")
        private String satisfaction;

        public Create() {
        }

        @Builder
        public Create(String review, String temperature, String humidity, String satisfaction) {
            this.review = review;
            this.temperature = temperature;
            this.humidity = humidity;
            this.satisfaction = satisfaction;
        }

        public static Ootd toEntity(OotdDTO.Create ootdDTO) {
            return Ootd.builder()
                    .review(ootdDTO.getReview())
                    .temperature(ootdDTO.getTemperature())
                    .humidity(ootdDTO.getHumidity())
                    .satisfaction(ootdDTO.getSatisfaction())
                    .build();
        }
    }

    @Setter
    @Getter
    public static class Update {
        @Schema(description = "OOTD 후기")
        private String review;

        @Schema(description = "OOTD 작성할 때 온도")
        private String temperature;

        @Schema(description = "OOTD 작성할 때 습도")
        private String humidity;

        @Schema(description = "OOTD 작성할 때 만족도", example = "Y 또는 N으로 값을 입력해주세요.")
        private String satisfaction;

        public Update() {
        }

        @Builder
        public Update(Long ootdId, String review, String temperature, String humidity, String satisfaction) {
            this.review = review;
            this.temperature = temperature;
            this.humidity = humidity;
            this.satisfaction = satisfaction;
        }

        public static Ootd toEntity(OotdDTO.Update ootdDTO) {
            return Ootd.builder()
                    .review(ootdDTO.getReview())
                    .temperature(ootdDTO.getTemperature())
                    .humidity(ootdDTO.getHumidity())
                    .satisfaction(ootdDTO.getSatisfaction())
                    .build();
        }
    }

    @Setter
    @Getter
    public static class Request {
        @Schema(description = "OOTD 게시물 번호", example = "신규 등록시 입력하지 말아주세요.")
        private Long ootdId;

        @Schema(description = "OOTD 후기")
        private String review;

        @Schema(description = "OOTD 작성할 때 온도")
        private String temperature;

        @Schema(description = "OOTD 작성할 때 습도")
        private String humidity;

        @Schema(description = "OOTD 작성할 때 만족도", example = "Y 또는 N으로 값을 입력해주세요.")
        private String satisfaction;

        public Request() {
        }

        @Builder
        public Request(Long ootdId, String review, String temperature, String humidity, String satisfaction) {
            this.ootdId = ootdId;
            this.review = review;
            this.temperature = temperature;
            this.humidity = humidity;
            this.satisfaction = satisfaction;
        }

        public static Ootd toEntity(OotdDTO.Request ootdDTO) {
            return Ootd.builder()
                    .ootdId(ootdDTO.getOotdId())
                    .review(ootdDTO.getReview())
                    .temperature(ootdDTO.getTemperature())
                    .humidity(ootdDTO.getHumidity())
                    .satisfaction(ootdDTO.getSatisfaction())
                    .build();
        }
    }

    @Setter
    @Getter
    public static class Response {
        @Schema(description = "OOTD 게시물 번호")
        private Long ootdId;
        
        @Schema(description = "OOTD 후기")
        private String review;
        
        @Schema(description = "OOTD 작성할 때 온도")
        private String temperature;
        
        @Schema(description = "OOTD 작성할 때 습도")
        private String humidity;
        
        @Schema(description = "OOTD 작성할 때 만족도")
        private String satisfaction;

        @Schema(description = "OOTD 작성일자")
        private String issueDate;
        
        @Schema(description = "OOTD 작성할 때 등록한 이미지")
        private List<OotdImageDTO.Response> ootdImages;
        
        @Schema(description = "OOTD 작성자 정보")
        private MemberDTO.Response member;

        public Response() {
        }

        @Builder
        public Response(Long ootdId, String review, String temperature, String humidity, String satisfaction, String issueDate, List<OotdImageDTO.Response> ootdImages, MemberDTO.Response member) {
            this.ootdId = ootdId;
            this.review = review;
            this.temperature = temperature;
            this.humidity = humidity;
            this.satisfaction = satisfaction;
            this.issueDate = issueDate;
            this.ootdImages = ootdImages;
            this.member = member;
        }

        public static OotdDTO.Response of(Ootd ootd) {
            return Response.builder()
                    .ootdId(ootd.getOotdId())
                    .review(ootd.getReview())
                    .temperature(ootd.getTemperature())
                    .humidity(ootd.getHumidity())
                    .satisfaction(ootd.getSatisfaction())
                    .issueDate(DateUtils.formatIssueDate(ootd.getCreatedAt()))
                    .ootdImages(OotdImageDTO.Response.listOf(ootd.getOotdImages()))
                    .member(MemberDTO.Response.of(ootd.getMember()))
                    .build();
        }

        public static List<OotdDTO.Response> listOf(List<Ootd> ootds) {
            List<OotdDTO.Response> responses = new ArrayList<>();
            for (Ootd ootd : ootds) {
                responses.add(OotdDTO.Response.of(ootd));
            }

            return responses;
        }
    }
}