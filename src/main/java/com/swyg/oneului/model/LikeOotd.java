package com.swyg.oneului.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class LikeOotd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeOotdId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ootd_id")
    private Ootd ootd;

    @Builder
    public LikeOotd(Long likeOotdId, Member member, Ootd ootd) {
        this.likeOotdId = likeOotdId;
        this.member = member;
        this.ootd = ootd;
    }

    public void initMember(Member member) {
        this.member = member;
    }
}