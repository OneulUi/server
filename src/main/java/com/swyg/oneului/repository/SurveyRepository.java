package com.swyg.oneului.repository;

import com.swyg.oneului.model.Survey;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class SurveyRepository {
    private final EntityManager entityManager;

    public void save(Survey survey) {
        entityManager.persist(survey);
    }

    public Optional<Survey> findSurveyBySurveyId(Long surveyId) {
        return entityManager.createQuery("select s from Survey s where s.surveyId = :surveyId", Survey.class)
                .setParameter("surveyId", surveyId)
                .getResultList()
                .stream()
                .findFirst();
    }

    public List<Survey> findAllSurveys() {
        return entityManager.createQuery("select s from Survey s", Survey.class)
                .getResultList();
    }
}