package com.swyg.oneului.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class BookMarkOotd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookMarkOotdId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ootdId")
    private Ootd ootd;

    public BookMarkOotd() {
    }

    @Builder
    public BookMarkOotd(Long bookMarkOotdId, Member member, Ootd ootd) {
        this.bookMarkOotdId = bookMarkOotdId;
        this.member = member;
        this.ootd = ootd;
    }

    public void initMember(Member member) {
        this.member = member;
    }
}