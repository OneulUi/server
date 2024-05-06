package com.swyg.oneului.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Ootd extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ootdId;
    private String review;
    private String temperature;
    private String humidity;
    private String satisfaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "ootd")
    private List<OotdImage> ootdImages = new ArrayList<>();

    public Ootd() {
    }

    @Builder
    public Ootd(Long ootdId, String review, String temperature, String humidity, String satisfaction) {
        this.ootdId = ootdId;
        this.review = review;
        this.temperature = temperature;
        this.humidity = humidity;
        this.satisfaction = satisfaction;
    }

    public void initMember(Member member) {
        this.member = member;
    }

    public void addOotdImage(OotdImage ootdImage) {
        this.ootdImages.add(ootdImage);
        ootdImage.initOotd(this);
    }

    public void modifyOotd(Ootd ootd) {
        this.review = ootd.getReview();
        this.temperature = ootd.getTemperature();
        this.humidity = ootd.getHumidity();
        this.satisfaction = ootd.getSatisfaction();
    }
}