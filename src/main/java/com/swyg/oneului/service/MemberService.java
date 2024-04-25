package com.swyg.oneului.service;

import com.swyg.oneului.model.Member;
import com.swyg.oneului.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public Member findMemberByLoginId(String loginId) {
        List<Member> members = memberRepository.findMemberByLoginId(loginId);
        if (!members.isEmpty()) {
            return members.get(0);
        }
        return null;
    }
}
