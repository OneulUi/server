package com.swyg.oneului.service;

import com.swyg.oneului.model.Member;
import com.swyg.oneului.model.OAuth2Details;
import com.swyg.oneului.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OAuth2UserService extends DefaultOAuth2UserService {
    private final MemberService memberService;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        Member member = findUserByLoginIdOrSaveIfNotFound(Member.toEntity(provider, oAuth2User.getAttributes()));

        return new OAuth2Details(member, oAuth2User.getAttributes());
    }

    private Member findUserByLoginIdOrSaveIfNotFound(Member member) {
        return memberService.findMemberByLoginIdOrSaveInNotFound(member);
    }
}