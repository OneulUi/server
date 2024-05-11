package com.swyg.oneului.repository;

import com.swyg.oneului.model.Member;
import com.swyg.oneului.model.Ootd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OotdRepository extends JpaRepository<Ootd, Long> {
    @Query("select o from Ootd o where o.temperature = :temperature")
    public List<Ootd> findAllOotdsByTemperature(@Param("temperature") String temperature);

    @Query("select o from Ootd o where o.humidity = :humidity")
    public List<Ootd> findAllOotdsByHumidity(@Param("humidity") String humidity);

    @Query("select o from Ootd o where o.member = :member and o.ootdId = :ootdId")
    public Ootd findOotdByMemberAndOotdId(@Param("member") Member member, @Param("ootdId") Long ootdId);

    @Query("select o from Ootd o where o.temperature = :temperature and o.humidity = :humidity")
    public List<Ootd> findAllOotdsByTemperatureAndHumidity(@Param("temperature") String temperature, @Param("humidity") String humidity);
}