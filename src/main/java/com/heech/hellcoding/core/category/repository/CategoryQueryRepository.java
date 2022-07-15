package com.heech.hellcoding.core.category.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CategoryQueryRepository {

    private final JPAQueryFactory queryFactory;

    public CategoryQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
}
