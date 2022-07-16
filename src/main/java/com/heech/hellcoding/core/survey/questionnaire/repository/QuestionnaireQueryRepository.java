package com.heech.hellcoding.core.survey.questionnaire.repository;

import com.heech.hellcoding.core.common.dto.SearchCondition;
import com.heech.hellcoding.core.survey.questionnaire.domain.Questionnaire;
import com.heech.hellcoding.core.survey.questionnaire.dto.QuestionnaireSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.heech.hellcoding.core.member.domain.QMember.member;
import static com.heech.hellcoding.core.survey.questionnaire.domain.QQuestionnaire.questionnaire;
import static org.springframework.util.StringUtils.*;

@Repository
public class QuestionnaireQueryRepository {

    private final JPAQueryFactory queryFactory;

    public QuestionnaireQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 설문 목록 조회
     */
    public Page<Questionnaire> findQuestionnaires(QuestionnaireSearchCondition condition, Pageable pageable) {
        List<Questionnaire> content = getQuestionnaireList(condition, pageable);

        JPAQuery<Long> count = getQuestionnaireListCount(condition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    /**
     * 설문 목록
     */
    private List<Questionnaire> getQuestionnaireList(QuestionnaireSearchCondition condition, Pageable pageable) {
        return queryFactory
                .select(questionnaire)
                .from(questionnaire)
                .where(
                        questionnaire.isDelete.eq("N"),
                        searchCondition(condition.getSearchCondition(), condition.getSearchKeyword())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    /**
     * 설문 목록 카운트
     */
    private JPAQuery<Long> getQuestionnaireListCount(QuestionnaireSearchCondition condition) {
        return queryFactory
                .select(questionnaire.count())
                .from(questionnaire)
                .where(
                        questionnaire.isDelete.eq("N"),
                        searchCondition(condition.getSearchCondition(), condition.getSearchKeyword())
                );
    }

    /**
     * name like '%searchKeyword%'
     * title like '%searchKeyword%'
     * content like '%searchKeyword%'
     */
    private BooleanExpression searchCondition(SearchCondition searchCondition, String searchKeyword) {
        if (!hasText(searchKeyword)) return null;
        if (SearchCondition.TITLE.equals(searchCondition)) {
            return questionnaire.title.contains(searchKeyword);
        }
        return null;
    }
}
