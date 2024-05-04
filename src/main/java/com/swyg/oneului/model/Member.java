package com.swyg.oneului.model;

import com.swyg.oneului.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Entity
@NoArgsConstructor
public class Member extends BaseEntity {
    // TODO loginId가 두 번 들어왔을 때, Exception 처리해야함
    // TODO RefreshToken 캐시로 변경 예정
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

    private String refreshToken;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @Builder
    public Member(Long memberId, String email, String name, String introduction, String backgroundColor, String loginId, String provider, String providerId, String refreshToken, MemberRole role, Survey survey) {
        this.memberId = memberId;
        this.email = email;
        this.name = name;
        this.introduction = introduction;
        this.backgroundColor = backgroundColor;
        this.loginId = loginId;
        this.provider = provider;
        this.providerId = providerId;
        this.refreshToken = refreshToken;
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

    public void updateProfile(Member member) {
        this.name = member.getName();
        this.introduction = member.getIntroduction();
        this.backgroundColor = member.getBackgroundColor();
    }

    public static Member of(MemberDTO memberDTO) {
        return Member.builder()
                .memberId(memberDTO.getUserId())
                .email(memberDTO.getEmail())
                .name(memberDTO.getName())
                .introduction(memberDTO.getIntroduction())
                .backgroundColor(memberDTO.getBackgroundColor())
                .role(memberDTO.getRole())
                .build();
    }
}