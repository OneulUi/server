package com.swyg.oneului.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Entity
@NoArgsConstructor
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    private String email;
    private String name;
    private String introduction;
    private String backgroundColor;
    @Column(unique = true)
    private String loginId;
    private String provider;
    private String providerId;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @OneToMany(mappedBy = "member")
    private List<Ootd> ootds = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<LikeOotd> likeOotds = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<BookMarkOotd> bookMarkOotds = new ArrayList<>();

    @Builder
    public Member(Long memberId, String email, String name, String introduction, String backgroundColor, String loginId, String provider, String providerId, MemberRole role, Survey survey) {
        this.memberId = memberId;
        this.email = email;
        this.name = name;
        this.introduction = introduction;
        this.backgroundColor = backgroundColor;
        this.loginId = loginId;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
        this.survey = survey;
    }

    public static Member toEntity(String provider, Map<String, Object> attributes) {
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        String backgroundColor = "#ffffff";
        String providerId = (String) attributes.get("sub");
        String loginId = provider + "_" + providerId;

        return Member.builder()
                .email(email)
                .name(name)
                .backgroundColor(backgroundColor)
                .loginId(loginId)
                .provider(provider)
                .providerId(providerId)
                .role(MemberRole.USER)
                .build();
    }

    public void initSurvey(Survey survey) {
        this.survey = survey;
    }

    public void addOotd(Ootd ootd) {
        this.ootds.add(ootd);
        ootd.initMember(this);
    }

    public void addLikeOotd(LikeOotd likeOotd) {
        this.likeOotds.add(likeOotd);
        likeOotd.initMember(this);
    }

    public void addSaveOotd(BookMarkOotd bookMarkOotd) {
        this.bookMarkOotds.add(bookMarkOotd);
        bookMarkOotd.initMember(this);
    }

    public void updateProfile(Member member) {
        this.name = member.getName();
        this.introduction = member.getIntroduction();
        this.backgroundColor = member.getBackgroundColor();
    }
}