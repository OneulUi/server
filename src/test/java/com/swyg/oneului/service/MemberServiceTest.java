package com.swyg.oneului.service;

import com.swyg.oneului.model.Member;
import com.swyg.oneului.model.MemberRole;
import com.swyg.oneului.model.Survey;
import com.swyg.oneului.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    public void 로그인ID로_사용자_정보를_조회한다() throws Exception {
        // given
        String loginId = "myLoginId";
        Member memberFixture = creatMemberFixtureWithoutSurvey(loginId);
        when(memberRepository.findMemberByLoginId(loginId)).thenReturn(Optional.of(memberFixture));

        // when
        Member foundMember = memberService.findMemberByLoginId(loginId);

        // then
        assertThat(memberFixture.getLoginId()).isEqualTo(foundMember.getLoginId());
    }

    @Test
    public void 로그인ID로_조회한_사용자가_없다면_예외를_발생시킨다() throws Exception {
        // given
        String loginId = "myLoginId";

        // when
        when(memberRepository.findMemberByLoginId(anyString())).thenReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> memberService.findMemberByLoginId(loginId))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void 사용자가_설문정보를_등록한다() {
        // given
        String loginId = "myLoginId";
        Survey survey = createSurveyFixture();

        Member member = creatMemberFixtureWithoutSurvey(loginId);
        when(memberRepository.findMemberByLoginId(loginId)).thenReturn(Optional.of(member));

        // when
        memberService.updateMemberSurvey(loginId, survey);

        // then
        assertThat(member.getSurvey().getSurveyId()).isEqualTo(survey.getSurveyId());
    }

    @Test
    public void 사용자가_프로필을_등록한다() throws Exception {
        // given
        String loginId = "myLoginId";
        String profile = "myProfile";
        Member member = creatMemberFixtureWithoutSurvey(loginId);
        Member profileInfo = Member.builder()
                .introduction(profile)
                .build();

        when(memberRepository.findMemberByLoginId(loginId)).thenReturn(Optional.of(member));

        // when
        memberService.updateMemberProfile(loginId, profileInfo);

        // then
        assertThat(member.getIntroduction()).isEqualTo(profile);
    }

    // 설문정보, 프로필 없이 기본정보만 생성
    private Member creatMemberFixtureWithoutSurvey(String loginId) {
        return Member.builder()
                .memberId(1001L)
                .email("member@gmail.com")
                .name("오늘의")
                .loginId(loginId)
                .provider("GOOGLE")
                .role(MemberRole.USER)
                .build();
    }

    private Survey createSurveyFixture() {
        return new Survey(1L, "option", 1);
    }
}
