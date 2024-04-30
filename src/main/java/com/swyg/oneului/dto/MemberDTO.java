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
    private SurveyDTO survey;

    public MemberDTO() {
    }

    @Builder
    public MemberDTO(Long userId, String email, String name, MemberRole role, SurveyDTO survey) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.role = role;
        this.survey = survey;
    }

    public static MemberDTO of(Member member) {
        MemberDTO memberDTO = MemberDTO.builder()
                .userId(member.getUserId())
                .email(member.getEmail())
                .name(member.getName())
                .role(member.getRole())
                .build();

        if (member.getSurvey() != null) {
            memberDTO.setSurvey(SurveyDTO.of(member.getSurvey()));
        }

        return memberDTO;
    }
}
