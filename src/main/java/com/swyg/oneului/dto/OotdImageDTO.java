package com.swyg.oneului.dto;

import com.swyg.oneului.model.OotdImage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class OotdImageDTO {
    @Setter
    @Getter
    public static class Request {
        @Schema(description = "OOTD 이미지 고유 번호")
        private Long ootdImageId;

        @Schema(description = "파일명")
        private String fileName;

        public Request() {
        }

        @Builder
        public Request(Long ootdImageId, String fileName) {
            this.ootdImageId = ootdImageId;
            this.fileName = fileName;
        }

        public static OotdImage toEntity(OotdImageDTO.Request ootdImageDTO) {
            return OotdImage.builder()
                    .ootdImageId(ootdImageDTO.getOotdImageId())
                    .fileName(ootdImageDTO.getFileName())
                    .build();
        }
    }

    @Setter
    @Getter
    public static class Response {
        @Schema(description = "OOTD 이미지 고유 번호")
        private Long ootdImageId;

        @Schema(description = "파일명")
        private String fileName;

        public Response() {
        }

        @Builder
        public Response(Long ootdImageId, String fileName) {
            this.ootdImageId = ootdImageId;
            this.fileName = fileName;
        }

        public static OotdImageDTO.Response of(OotdImage ootdImage) {
            return OotdImageDTO.Response.builder()
                    .ootdImageId(ootdImage.getOotdImageId())
                    .fileName(ootdImage.getFileName())
                    .build();
        }

        public static List<OotdImageDTO.Response> listOf(List<OotdImage> ootdImages) {
            List<OotdImageDTO.Response> responses = new ArrayList<>();
            for (OotdImage ootdImage : ootdImages) {
                responses.add(OotdImageDTO.Response.of(ootdImage));
            }

            return responses;
        }
    }
}