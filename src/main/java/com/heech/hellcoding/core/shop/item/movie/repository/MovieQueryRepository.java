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
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.heech.hellcoding.core.member.domain.QMember.member;
import static com.heech.hellcoding.core.shop.item.book.domain.QBook.book;
import static com.heech.hellcoding.core.shop.item.movie.domain.QMovie.movie;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class MovieQueryRepository {

    private final JPAQueryFactory queryFactory;

    public MovieQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * item > movie 목록 조회
     */
    public Page<Movie> findMovies(MovieSearchCondition condition, Pageable pageable) {
        List<Movie> content = getMovieList(condition, pageable);

        JPAQuery<Long> count = getMovieListCount(condition);
        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    /**
     * item > movie 목록
     */
    private List<Movie> getMovieList(MovieSearchCondition condition, Pageable pageable) {
        return queryFactory
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
    }

    /**
     * item > movie 목록 카운트
     */
    private JPAQuery<Long> getMovieListCount(MovieSearchCondition condition) {
        return queryFactory
                .select(movie.count())
                .from(movie)
                .where(
                        searchCondition(condition.getSearchCondition(), condition.getSearchKeyword()),
                        searchPriceGoe(condition.getSearchPriceGoe()),
                        searchPriceLoe(condition.getSearchPriceLoe())
                );
    }

    /**
     * name like '%searchKeyword%'
     * title like '%searchKeyword%'
     * content like '%searchKeyword%'
     */
    private BooleanExpression searchCondition(SearchCondition searchCondition, String searchKeyword) {
        if (!hasText(searchKeyword)) return null;
        if (SearchCondition.NAME.equals(searchCondition)) {
            return movie.name.contains(searchKeyword);
        } else if (SearchCondition.TITLE.equals(searchCondition)) {
            return movie.title.contains(searchKeyword);
        } else if (SearchCondition.CONTENT.equals(searchCondition)) {
            return movie.content.contains(searchKeyword);
        }
        return null;
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
