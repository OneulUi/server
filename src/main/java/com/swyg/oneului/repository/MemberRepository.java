package com.swyg.oneului.repository;

import com.swyg.oneului.model.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class MemberRepository {
    private final EntityManager entityManager;

    public Member save(Member member) {
        entityManager.persist(member);
        return member;
    }

    public List<Member> findMemberByLoginId(String loginId) {
        return entityManager.createQuery("select m from Member m where m.loginId = :loginId", Member.class)
                .setParameter("loginId", loginId)
                .getResultList();
    }

    public void updateRefreshToken(String refreshToken, String loginId) {
        entityManager.createQuery("update Member m set m.refreshToken = :refreshToken where m.loginId = :loginId")
                .setParameter("refreshToken", refreshToken)
                .setParameter("loginId", loginId)
                .executeUpdate();
    }
}