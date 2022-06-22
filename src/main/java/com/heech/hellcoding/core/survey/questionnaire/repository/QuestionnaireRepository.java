package com.heech.hellcoding.core.survey.questionnaire.repository;

import com.heech.hellcoding.core.survey.questionnaire.domain.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long>, QuestionnaireRepositoryQuerydsl {
}
