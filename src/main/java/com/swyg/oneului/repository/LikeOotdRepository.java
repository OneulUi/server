package com.swyg.oneului.repository;

import com.swyg.oneului.model.LikeOotd;
import com.swyg.oneului.model.Member;
import com.swyg.oneului.model.Ootd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeOotdRepository extends JpaRepository<LikeOotd, Long> {
    @Query("select l from LikeOotd l where l.member = :member")
    public List<LikeOotd> findAllLikeOotdByMember(@Param("member") Member member);

    @Query("select l from LikeOotd l where l.member = :member and l.ootd = :ootd")
    public List<LikeOotd> findAllLikeOotdByMemberAndOotd(@Param("member") Member member, @Param("ootd") Ootd ootd);

    @Modifying
    @Query("delete from LikeOotd l where l.member = :member and l.ootd = :ootd")
    public void deleteLikeOotdByMemberAndOotd(@Param("member") Member member, @Param("ootd") Ootd ootd);

    @Modifying
    @Query("delete from LikeOotd l where l.ootd = :ootd")
    public void deleteLikeOotdByOotd(@Param("ootd") Ootd ootd);
}
