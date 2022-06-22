package com.heech.hellcoding.core.shop.item.book.repository;

import com.heech.hellcoding.core.common.dto.SearchCondition;
import com.heech.hellcoding.core.shop.item.book.domain.Book;
import com.heech.hellcoding.core.shop.item.book.dto.BookSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;

import java.util.List;

import static com.heech.hellcoding.core.shop.item.book.domain.QBook.*;
import static org.springframework.util.StringUtils.hasText;

public class BookRepositoryImpl implements BookRepositoryQuerydsl{

    private final JPAQueryFactory queryFactory;

    public BookRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Book> findBooks(BookSearchCondition condition, Pageable pageable) {
        List<Book> content = getBookList(condition, pageable);

        JPAQuery<Long> count = getBookListCount(condition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    /**
     * Book 목록
     */
    private List<Book> getBookList(BookSearchCondition condition, Pageable pageable) {
        List<Book> content = queryFactory
                .select(book)
                .from(book)
                .leftJoin(book.category)
                .fetchJoin()
                .where(
                        searchCondition(condition.getSearchCondition(), condition.getSearchKeyword()),
                        searchPriceGoe(condition.getSearchPriceGoe()),
                        searchPriceLoe(condition.getSearchPriceLoe())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return content;
    }

    /**
     * Book 목록 카운트
     */
    private JPAQuery<Long> getBookListCount(BookSearchCondition condition) {
        JPAQuery<Long> count = queryFactory
                .select(book.count())
                .from(book)
                .where(
                        searchCondition(condition.getSearchCondition(), condition.getSearchKeyword()),
                        searchPriceGoe(condition.getSearchPriceGoe()),
                        searchPriceLoe(condition.getSearchPriceLoe())
                );
        return count;
    }

    private BooleanExpression searchCondition(SearchCondition searchCondition, String searchKeyword) {
        if (SearchCondition.NAME.equals(searchCondition)) {
            return book.name.contains(searchKeyword);
        } else if (SearchCondition.TITLE.equals(searchCondition)) {
            return book.title.contains(searchKeyword);
        } else if (SearchCondition.CONTENT.equals(searchCondition)) {
            return book.content.contains(searchKeyword);
        } else {
            return null;
        }
    }

    /**
     * searchPrice >= item.price
     */
    private BooleanExpression searchPriceGoe(Integer searchPriceGoe) {
        return searchPriceGoe != null ? book.price.goe(searchPriceGoe) : null;
    }

    /**
     * searchPrice <= item.price
     */
    private BooleanExpression searchPriceLoe(Integer searchPriceLoe) {
        return searchPriceLoe != null ? book.price.loe(searchPriceLoe) : null;
    }
}
