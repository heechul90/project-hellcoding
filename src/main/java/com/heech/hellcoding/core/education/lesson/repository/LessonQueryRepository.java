package com.heech.hellcoding.core.education.lesson.repository;

import com.heech.hellcoding.core.education.lesson.domain.Lesson;
import com.heech.hellcoding.core.education.lesson.dto.LessonSearchCondition;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class LessonQueryRepository {

    private final JPAQueryFactory queryFactory;

    public LessonQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Page<Lesson> findLessons(LessonSearchCondition condition, Pageable pageable) {
        return null;
    }
}
