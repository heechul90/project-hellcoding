package com.heech.hellcoding.core.survey.questionnaire.repository;

import com.heech.hellcoding.core.survey.questionnaire.domain.Questionnaire;
import com.heech.hellcoding.core.survey.questionnaire.dto.QuestionnaireSearchCondition;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

public class QuestionnaireRepositoryImpl implements QuestionnaireRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    public QuestionnaireRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Page<Questionnaire> findForms(QuestionnaireSearchCondition condition, Pageable pageable) {
        return null;
    }
}
