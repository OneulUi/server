package com.swyg.oneului.repository;

import com.swyg.oneului.model.BookMarkOotd;
import com.swyg.oneului.model.Member;
import com.swyg.oneului.model.Ootd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookMarkOotdRepository extends JpaRepository<BookMarkOotd, Long> {

    @Query("select b from BookMarkOotd b where b.member = :member")
    public List<BookMarkOotd> findBookMarkOotdsByMember(@Param("member") Member member);

    @Query("select b from BookMarkOotd b where b.member = :member and b.ootd = :ootd")
    public List<BookMarkOotd> findBookMarkOotdsByMemberAndOotd(@Param("member") Member member, @Param("ootd") Ootd ootd);

    @Modifying
    @Query("delete from BookMarkOotd b where b.member = :member and b.ootd = :ootd")
    public void deleteBookMarkOotdByMemberAndOotd(@Param("member") Member member, @Param("ootd") Ootd ootd);

    @Modifying
    @Query("delete from BookMarkOotd b where b.ootd = :ootd")
    public void deleteBookMarkOotdByOotd(@Param("ootd") Ootd ootd);
}