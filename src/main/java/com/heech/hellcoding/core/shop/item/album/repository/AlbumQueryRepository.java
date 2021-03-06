package com.heech.hellcoding.core.shop.item.album.repository;

import com.heech.hellcoding.core.common.dto.SearchCondition;
import com.heech.hellcoding.core.shop.item.album.domain.Album;
import com.heech.hellcoding.core.shop.item.album.dto.AlbumSearchCondition;
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

import static com.heech.hellcoding.core.shop.item.album.domain.QAlbum.album;
import static org.springframework.util.StringUtils.*;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class AlbumQueryRepository {

    private final JPAQueryFactory queryFactory;

    public AlbumQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * item > album 목록 조회
     */
    public Page<Album> findAlbums(AlbumSearchCondition condition, Pageable pageable) {
        List<Album> content = getAlbumList(condition, pageable);

        JPAQuery<Long> count = getAlbumListCount(condition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    /**
     * item > album 목록
     */
    private List<Album> getAlbumList(AlbumSearchCondition condition, Pageable pageable) {
        return queryFactory
                .select(album)
                .from(album)
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
     * item > album 목록 카운트
     */
    private JPAQuery<Long> getAlbumListCount(AlbumSearchCondition condition) {
        return queryFactory
                .select(album.count())
                .from(album)
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
            return album.name.contains(searchKeyword);
        } else if (SearchCondition.TITLE.equals(searchCondition)) {
            return album.title.contains(searchKeyword);
        } else if (SearchCondition.CONTENT.equals(searchCondition)) {
            return album.content.contains(searchKeyword);
        }
        return null;
    }

    /**
     * searchPrice >= item.price
     */
    private BooleanExpression searchPriceGoe(Integer searchPriceGoe) {
        return searchPriceGoe != null ? album.price.goe(searchPriceGoe) : null;
    }

    /**
     * searchPrice <= item.price
     */
    private BooleanExpression searchPriceLoe(Integer searchPriceLoe) {
        return searchPriceLoe != null ? album.price.loe(searchPriceLoe) : null;
    }
}
