package com.heech.hellcoding.core.survey.questionnaire.repository;

import com.heech.hellcoding.core.survey.questionnaire.domain.Questionnaire;
import com.heech.hellcoding.core.survey.questionnaire.dto.QuestionnaireSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionnaireRepositoryQuerydsl {

    Page<Questionnaire> findForms(QuestionnaireSearchCondition condition, Pageable pageable);
}
