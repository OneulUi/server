package com.swyg.oneului.dto;

import com.swyg.oneului.model.Member;
import com.swyg.oneului.model.MemberRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class MemberDTO {
    @Setter
    @Getter
    public static class Request {
        @Schema(description = "회원 이메일")
        private String email;

        @Schema(description = "회원 이름")
        private String name;

        @Schema(description = "회원 소개")
        private String introduction;

        @Schema(description = "배경 색상", example = "#fffff와 같은 형태로 작성해주세요.")
        private String backgroundColor;

        public Request() {
        }

        @Builder
        public Request(String email, String name, String introduction, String backgroundColor) {
            this.email = email;
            this.name = name;
            this.introduction = introduction;
            this.backgroundColor = backgroundColor;
        }

        public static Member toEntity(MemberDTO.Request memberDTO) {
            return Member.builder()
                    .email(memberDTO.getEmail())
                    .name(memberDTO.getName())
                    .introduction(memberDTO.getIntroduction())
                    .backgroundColor(memberDTO.getBackgroundColor())
                    .build();
        }
    }

    @Setter
    @Getter
    public static class Response {
        @Schema(description = "회원 고유 번호")
        private Long memberId;

        @Schema(description = "회원 이메일")
        private String email;

        @Schema(description = "회원 이름")
        private String name;

        @Schema(description = "회원 소개")
        private String introduction;

        @Schema(description = "배경 색상")
        private String backgroundColor;

        @Schema(description = "회원이 선택한 설문 정보")
        private SurveyDTO.Response survey;

        public Response() {
        }

        @Builder
        public Response(Long memberId, String email, String name, String introduction, String backgroundColor, SurveyDTO.Response survey) {
            this.memberId = memberId;
            this.email = email;
            this.name = name;
            this.introduction = introduction;
            this.backgroundColor = backgroundColor;
            this.survey = survey;
        }

        public static MemberDTO.Response of(Member member) {
            return Response.builder()
                    .memberId(member.getMemberId())
                    .email(member.getEmail())
                    .name(member.getName())
                    .introduction(member.getIntroduction())
                    .backgroundColor(member.getBackgroundColor())
                    .survey(member.getSurvey() != null ? SurveyDTO.Response.of(member.getSurvey()) : null)
                    .build();
        }
    }
}