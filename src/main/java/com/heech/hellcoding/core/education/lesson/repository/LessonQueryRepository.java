package com.heech.hellcoding.core.education.lesson.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class LessonQueryRepository {

    private final JPAQueryFactory queryFactory;

    public LessonQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
}
