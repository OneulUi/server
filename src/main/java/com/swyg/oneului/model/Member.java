package com.swyg.oneului.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Entity
public class Member extends BaseEntity {
    // TODO loginId가 두 번 들어왔을 때, Exception 처리해야함
    // TODO RefreshToken 캐시로 변경 예정
    @Id
    @GeneratedValue
    private Long userId;
    private String email;
    private String name;
    @Column(unique = true)
    private String loginId;
    private String provider;
    private String providerId;
    private String refreshToken;
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    private Survey survey;

    public Member() {
    }

    @Builder
    public Member(Long userId, String email, String name, String loginId, String provider, String providerId, String refreshToken, MemberRole role) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.loginId = loginId;
        this.provider = provider;
        this.providerId = providerId;
        this.refreshToken = refreshToken;
        this.role = role;
    }

    public static Member toEntity(String provider, Map<String, Object> attributes) {
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        String providerId = (String) attributes.get("sub");
        String loginId = provider + "_" + providerId;

        return Member.builder()
                .email(email)
                .name(name)
                .loginId(loginId)
                .provider(provider)
                .providerId(providerId)
                .role(MemberRole.USER)
                .build();
    }

    public void initSurvey(Survey survey) {
        this.survey = survey;
    }
}