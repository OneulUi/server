package com.swyg.oneului.repository;

import com.swyg.oneului.model.Ootd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OotdRepository extends JpaRepository<Ootd, Long> {

}