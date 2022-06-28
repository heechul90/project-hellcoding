package com.heech.hellcoding.core.survey.result.repository;

import com.heech.hellcoding.core.survey.result.domain.QuestionnaireResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionnaireResultRepository extends JpaRepository<QuestionnaireResult, Long> {
}
