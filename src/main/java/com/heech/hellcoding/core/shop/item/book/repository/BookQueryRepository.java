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
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.heech.hellcoding.core.shop.item.book.domain.QBook.book;

@Repository
public class BookQueryRepository {

    private final JPAQueryFactory queryFactory;

    public BookQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * item > book 목록 조회
     */
    public Page<Book> findBooks(BookSearchCondition condition, Pageable pageable) {
        List<Book> content = getBookList(condition, pageable);

        JPAQuery<Long> count = getBookListCount(condition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    /**
     * item > book 목록
     */
    private List<Book> getBookList(BookSearchCondition condition, Pageable pageable) {
        return queryFactory
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
    }

    /**
     * item > book 목록 카운트
     */
    private JPAQuery<Long> getBookListCount(BookSearchCondition condition) {
        return queryFactory
                .select(book.count())
                .from(book)
                .where(
                        searchCondition(condition.getSearchCondition(), condition.getSearchKeyword()),
                        searchPriceGoe(condition.getSearchPriceGoe()),
                        searchPriceLoe(condition.getSearchPriceLoe())
                );
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
