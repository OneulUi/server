package com.swyg.oneului.service;

import com.swyg.oneului.model.Member;
import com.swyg.oneului.model.Survey;
import com.swyg.oneului.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    private Member getMemberEntityByLoginId(String loginId) {
        List<Member> members = memberRepository.findMemberByLoginId(loginId);
        if (!members.isEmpty()) {
            return members.get(0);
        }

        throw new NoSuchElementException("존재하지 않는 회원입니다.");
    }

    public Member findMemberByLoginId(String loginId) {
        return getMemberEntityByLoginId(loginId);
    }

    public Member findMemberAndSurveyByLoginId(String loginId) {
        Member member = getMemberEntityByLoginId(loginId);
        Survey survey = member.getSurvey();

        return member;
    }

    @Transactional
    public void updateMemberSurvey(String loginId, Survey survey) {
        Member member = getMemberEntityByLoginId(loginId);
        member.initSurvey(survey);
    }

    @Transactional
    public void updateMemberProfile(String loginId, Member member) {
        Member loginedMember = getMemberEntityByLoginId(loginId);
        loginedMember.updateProfile(member);
    }
}