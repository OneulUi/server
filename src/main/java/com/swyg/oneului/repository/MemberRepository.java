package com.swyg.oneului.repository;

import com.swyg.oneului.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findMemberByLoginId(String loginId);

    @Query("update Member m set m.refreshToken = :refreshToken where m.loginId = :loginId")
    void updateRefreshToken(@Param("refreshToken") String refreshToken, @Param("loginId") String loginId);
}