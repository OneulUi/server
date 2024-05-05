package com.swyg.oneului.repository;

import com.swyg.oneului.model.Ootd;
import com.swyg.oneului.model.OotdImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OotdImageRepository extends JpaRepository<OotdImage, Long> {
    List<OotdImage> findOotdImageByOotd(Ootd ootd);
}