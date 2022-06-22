package com.heech.hellcoding.core.survey.questionnaire.repository;

import com.heech.hellcoding.core.common.dto.SearchCondition;
import com.heech.hellcoding.core.survey.questionnaire.domain.QQuestionnaire;
import com.heech.hellcoding.core.survey.questionnaire.domain.Questionnaire;
import com.heech.hellcoding.core.survey.questionnaire.dto.QuestionnaireSearchCondition;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;

import java.util.List;

import static com.heech.hellcoding.core.survey.questionnaire.domain.QQuestionnaire.*;

public class QuestionnaireRepositoryImpl implements QuestionnaireRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    public QuestionnaireRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Questionnaire> findForms(QuestionnaireSearchCondition condition, Pageable pageable) {
        List<Questionnaire> content = getQuestionnaireList(condition, pageable);

        JPAQuery<Long> count = getQuestionnaireListCount(condition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    /**
     * 설문 목록
     */
    private List<Questionnaire> getQuestionnaireList(QuestionnaireSearchCondition condition, Pageable pageable) {
        List<Questionnaire> content = queryFactory
                .select(questionnaire)
                .from(questionnaire)
                .where(
                        questionnaire.useAt.eq("Y"),
                        searchCondition(condition.getSearchCondition(), condition.getSearchKeyword())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return content;
    }

    /**
     * 설문 목록 카운트
     */
    private JPAQuery<Long> getQuestionnaireListCount(QuestionnaireSearchCondition condition) {
        JPAQuery<Long> count = queryFactory
                .select(questionnaire.count())
                .from(questionnaire)
                .where(
                        questionnaire.useAt.eq("Y"),
                        searchCondition(condition.getSearchCondition(), condition.getSearchKeyword())
                );
        return count;
    }

    private BooleanExpression searchCondition(SearchCondition searchCondition, String searchKeyword) {
        if (SearchCondition.TITLE.equals(searchCondition)) {
            return questionnaire.title.contains(searchKeyword);
        } else {
            return null;
        }
    }
}
