package com.swyg.oneului.dto;

import com.swyg.oneului.model.OotdImage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class OotdImageDTO {
    private Long ootdImageId;
    private String fileName;

    @Builder
    public OotdImageDTO(Long ootdImageId, String fileName) {
        this.ootdImageId = ootdImageId;
        this.fileName = fileName;
    }

    public static OotdImageDTO of(OotdImage ootdImage) {
        return OotdImageDTO.builder()
                .ootdImageId(ootdImage.getOotdImageId())
                .fileName(ootdImage.getFileName())
                .build();
    }

    public static List<OotdImageDTO> listOf(List<OotdImage> ootdImages) {
        List<OotdImageDTO> ootdImageDTOS = new ArrayList<>();
        for (OotdImage ootdImage : ootdImages) {
            ootdImageDTOS.add(OotdImageDTO.of(ootdImage));
        }
        return ootdImageDTOS;
    }
}
