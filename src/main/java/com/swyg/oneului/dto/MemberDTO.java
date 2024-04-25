package com.swyg.oneului.dto;

import com.swyg.oneului.model.Member;
import com.swyg.oneului.model.MemberRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberDTO {
    private Long userId;
    private String email;
    private String name;
    private MemberRole role;

    public MemberDTO() {
    }

    @Builder
    public MemberDTO(Long userId, String email, String name, MemberRole role) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.role = role;
    }

    public static MemberDTO of(Member member) {
        return MemberDTO.builder()
                .userId(member.getUserId())
                .email(member.getEmail())
                .name(member.getName())
                .role(member.getRole())
                .build();
    }
}
