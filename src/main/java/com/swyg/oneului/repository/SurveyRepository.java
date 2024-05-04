package com.swyg.oneului.repository;

import com.swyg.oneului.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {

    Optional<Survey> findSurveyBySurveyId(Long surveyId);

    List<Survey> findAll();
}