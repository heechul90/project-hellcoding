package com.heech.hellcoding.core.shop.category.repository;

import com.heech.hellcoding.core.common.dto.SearchCondition;
import com.heech.hellcoding.core.shop.category.domain.Category;
import com.heech.hellcoding.core.shop.category.dto.CategorySearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;

import java.util.List;

import static com.heech.hellcoding.core.shop.category.domain.QCategory.*;

public class CategoryRepositoryImpl implements CategoryRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    public CategoryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Category> findCategories(CategorySearchCondition condition, Pageable pageable) {
        List<Category> content = getCategoryList(condition, pageable);

        JPAQuery<Long> count = getCategoryListCount(condition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    /**
     * 카테고리 목록
     */
    private List<Category> getCategoryList(CategorySearchCondition condition, Pageable pageable) {
        List<Category> content = queryFactory
                .select(category)
                .from(category)
                .where(
                        searchCondition(condition.getSearchCondition(), condition.getSearchKeyword())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return content;
    }

    /**
     * 카테고리 목록 카운트
     */
    private JPAQuery<Long> getCategoryListCount(CategorySearchCondition condition) {
        JPAQuery<Long> count = queryFactory
                .select(category.count())
                .from(category)
                .where(
                        searchCondition(condition.getSearchCondition(), condition.getSearchKeyword())
                );
        return count;
    }

    private BooleanExpression searchCondition(SearchCondition searchCondition, String searchKeyword) {
        if (SearchCondition.NAME.equals(searchCondition)) {
            return category.name.contains(searchKeyword);
        } else if (SearchCondition.TITLE.equals(searchCondition)) {
            return category.title.contains(searchKeyword);
        } else if (SearchCondition.CONTENT.equals(searchCondition)) {
            return category.content.contains(searchKeyword);
        } else {
            return null;
        }
    }
}
