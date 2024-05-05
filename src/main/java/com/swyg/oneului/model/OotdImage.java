package com.swyg.oneului.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class OotdImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ootdImageId;
    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ootd_id")
    private Ootd ootd;

    public OotdImage() {
    }

    @Builder
    public OotdImage(Long ootdImageId, String fileName, Ootd ootd) {
        this.ootdImageId = ootdImageId;
        this.fileName = fileName;
        this.ootd = ootd;
    }

    public void initOotd(Ootd ootd) {
        this.ootd = ootd;
    }

    public void modifyFileName(String fileName) {
        this.fileName = fileName;
    }
}