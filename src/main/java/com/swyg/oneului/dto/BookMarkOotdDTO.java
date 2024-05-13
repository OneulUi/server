package com.swyg.oneului.dto;

import com.swyg.oneului.model.Ootd;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class BookMarkOotdDTO {
    @Setter
    @Getter
    public static class Create {
        private Long ootdId;

        public Create() {
        }

        @Builder
        public Create(Long ootdId) {
            this.ootdId = ootdId;
        }

        public static Ootd toEntity(BookMarkOotdDTO.Create bookMarkOotdDTO) {
            return Ootd.builder()
                    .ootdId(bookMarkOotdDTO.getOotdId())
                    .build();
        }
    }
}
