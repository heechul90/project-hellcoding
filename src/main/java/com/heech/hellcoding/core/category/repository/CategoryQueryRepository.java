package com.heech.hellcoding.core.category.repository;

import com.heech.hellcoding.core.category.domain.Category;
import com.heech.hellcoding.core.category.domain.QCategory;
import com.heech.hellcoding.core.category.dto.CategorySearchCondition;
import com.heech.hellcoding.core.common.dto.SearchCondition;
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

import static com.heech.hellcoding.core.category.domain.QCategory.*;
import static org.springframework.util.StringUtils.*;

@Repository
public class CategoryQueryRepository {

    private final JPAQueryFactory queryFactory;

    public CategoryQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 카테고리 목록 조회
     */
    public Page<Category> findCategories(CategorySearchCondition condition, Pageable pageable) {
        List<Category> content = getCategoryList(condition, pageable);

        JPAQuery<Long> count = getCategoryListCount(condition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    /**
     * 카테고리 목록
     */
    private List<Category> getCategoryList(CategorySearchCondition condition, Pageable pageable) {
        return queryFactory
                .select(category)
                .from(category)
                .where(
                        searchCondition(condition.getSearchCondition(), condition.getSearchKeyword())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    /**
     * 카테고리 목록 카운트
     */
    private JPAQuery<Long> getCategoryListCount(CategorySearchCondition condition) {
        return queryFactory
                .select(category.count())
                .from(category)
                .where(
                        searchCondition(condition.getSearchCondition(), condition.getSearchKeyword())
                );
    }

    private BooleanExpression searchCondition(SearchCondition searchCondition, String searchKeyword) {
        if (SearchCondition.NAME.equals(searchCondition)) {
            return category.name.contains(searchKeyword);
        } else if (SearchCondition.CONTENT.equals(searchCondition)) {
            return category.content.contains(searchKeyword);
        }
        return null;
    }
}
