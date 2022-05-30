package com.heech.hellcoding.core.shop.item.movie.repository;

import com.heech.hellcoding.core.common.dto.SearchCondition;
import com.heech.hellcoding.core.shop.item.movie.domain.Movie;
import com.heech.hellcoding.core.shop.item.movie.dto.MovieSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;

import java.util.List;

import static com.heech.hellcoding.core.shop.item.book.domain.QBook.book;
import static com.heech.hellcoding.core.shop.item.movie.domain.QMovie.*;
import static org.springframework.util.StringUtils.hasText;

public class MovieRepositoryImpl implements MovieRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    public MovieRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Movie> findMovies(MovieSearchCondition condition, Pageable pageable) {
        List<Movie> content = getMovieList(condition, pageable);

        JPAQuery<Long> count = getMovieListCount(condition);
        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    /**
     * Movie 목록
     */
    private List<Movie> getMovieList(MovieSearchCondition condition, Pageable pageable) {
        List<Movie> content = queryFactory
                .select(movie)
                .from(movie)
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
     * Movie 목록 카운트
     */
    private JPAQuery<Long> getMovieListCount(MovieSearchCondition condition) {
        JPAQuery<Long> count = queryFactory
                .select(movie.count())
                .from(movie)
                .where(
                        searchCondition(condition.getSearchCondition(), condition.getSearchKeyword()),
                        searchPriceGoe(condition.getSearchPriceGoe()),
                        searchPriceLoe(condition.getSearchPriceLoe())
                );
        return count;
    }

    private BooleanExpression searchCondition(SearchCondition searchCondition, String searchKeyword) {
        if (searchCondition == null || !hasText(searchKeyword)) {
            return null;
        }
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
