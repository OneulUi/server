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
public class TokenService {
    private final MemberRepository memberRepository;

    public String findMemberRefreshTokenByLoginId(String loginId) {
        List<Member> members = memberRepository.findMemberByLoginId(loginId);
        if (!members.isEmpty()) {
            return members.get(0).getRefreshToken();
        }
        return null;
    }

    @Transactional
    public void updateRefreshToken(String refreshToken, String loginId) {
        memberRepository.updateRefreshToken(refreshToken, loginId);
    }
}