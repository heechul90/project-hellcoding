package com.heech.hellcoding.core.education.lesson.repository;

import com.heech.hellcoding.core.common.dto.SearchCondition;
import com.heech.hellcoding.core.education.lesson.domain.Lesson;
import com.heech.hellcoding.core.education.lesson.domain.QLesson;
import com.heech.hellcoding.core.education.lesson.dto.LessonSearchCondition;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;

import java.util.List;

import static com.heech.hellcoding.core.education.lesson.domain.QLesson.*;
import static org.springframework.util.StringUtils.*;

@Repository
public class LessonQueryRepository {

    private final JPAQueryFactory queryFactory;

    public LessonQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * lesson 목록 조회
     */
    public Page<Lesson> findLessons(LessonSearchCondition condition, Pageable pageable) {

        List<Lesson> content = findLessonList(condition, pageable);

        JPAQuery<Long> count = findLessonListCount(condition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    /**
     * lesson 목록
     */
    private List<Lesson> findLessonList(LessonSearchCondition condition, Pageable pageable) {
        return queryFactory
                .select(lesson)
                .from(lesson)
                .where(
                        searchCondition(condition)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(lesson.id.desc())
                .fetch();
    }

    /**
     * lesson 목록 카운트
     */
    private JPAQuery<Long> findLessonListCount(LessonSearchCondition condition) {
        return queryFactory
                .select(lesson.count())
                .from(lesson)
                .where(
                        searchCondition(condition)
                );
    }

    /**
     * title like '%searchKeyword%'
     */
    private BooleanExpression searchCondition(LessonSearchCondition condition) {
        if (!hasText(condition.getSearchKeyword())) {
            return null;
        }
        if (condition.getSearchCondition().equals(SearchCondition.TITLE)) {
            return lesson.title.contains(condition.getSearchKeyword());
        }
        return null;
    }
}
