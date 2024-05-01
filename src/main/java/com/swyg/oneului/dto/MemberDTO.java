package com.swyg.oneului.dto;

import com.swyg.oneului.model.Member;
import com.swyg.oneului.model.MemberRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberDTO {
    @Schema(description = "회원 PK")
    private Long userId;
    @Schema(description = "회원 이메일")
    private String email;
    @Schema(description = "회원 이름")
    private String name;
    @Schema(description = "회원 권한")
    private MemberRole role;
    @Schema(description = "회원이 선택한 설문 정보")
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
