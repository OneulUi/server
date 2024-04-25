package com.swyg.oneului.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Entity
public class Member extends BaseEntity {
    @Id
    @GeneratedValue
    private Long userId;
    private String email;
    private String name;
    private String loginId;
    private String provider;
    private String providerId;
    private String refreshToken;
    @Enumerated(EnumType.STRING)
    private MemberRole role;

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
}
