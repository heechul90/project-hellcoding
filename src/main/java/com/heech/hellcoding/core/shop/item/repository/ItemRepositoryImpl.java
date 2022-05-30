package com.heech.hellcoding.core.shop.item.repository;

import com.heech.hellcoding.core.common.dto.SearchCondition;
import com.heech.hellcoding.core.shop.item.domain.Item;
import com.heech.hellcoding.core.shop.item.dto.ItemSearchCondition;
import com.heech.hellcoding.core.shop.item.dto.SearchItemType;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;

import java.util.List;

import static com.heech.hellcoding.core.shop.item.domain.QItem.*;
import static org.springframework.util.StringUtils.*;

public class ItemRepositoryImpl implements ItemReposityQuerydsl{

    private final JPAQueryFactory queryFactory;

    public ItemRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 상품 목록
     */
    @Override
    public Page<Item> findItems(ItemSearchCondition condition, Pageable pageable) {
        List<Item> content = getItemList(condition, pageable);

        JPAQuery<Long> count = getItemListCount(condition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    /**
     * 상품 목록
     */
    private JPAQuery<Long> getItemListCount(ItemSearchCondition condition) {
        JPAQuery<Long> count = queryFactory
                .select(item.count())
                .from(item)
                .where(
                        searchCondition(condition.getSearchCondition(), condition.getSearchKeyword()),
                        searchPriceGoe(condition.getSearchPriceGoe()),
                        searchPriceLoe(condition.getSearchPriceLoe())
                );
        return count;
    }

    /**
     * 상품 목록 카운트
     */
    private List<Item> getItemList(ItemSearchCondition condition, Pageable pageable) {
        List<Item> content = queryFactory
                .select(item)
                .from(item)
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

    private BooleanExpression searchCondition(SearchCondition searchCondition, String searchKeyword) {
        if (searchCondition == null || !hasText(searchKeyword)) {
            return null;
        }
        if (SearchCondition.NAME.equals(searchCondition)) {
            return item.name.contains(searchKeyword);
        } else if (SearchCondition.TITLE.equals(searchCondition)) {
            return item.title.contains(searchKeyword);
        } else if (SearchCondition.CONTENT.equals(searchCondition)) {
            return item.content.contains(searchKeyword);
        } else {
            return null;
        }
    }

    private BooleanExpression searchItemType(SearchItemType searchItemType) {
        if (searchItemType == null) {
            return null;
        }
        if (SearchItemType.BOOK.equals(searchItemType)) {
            //return item.dtype
        }
        return null;
    }

    /**
     * searchPrice >= item.price
     */
    private BooleanExpression searchPriceGoe(Integer searchPriceGoe) {
        return searchPriceGoe != null ? item.price.goe(searchPriceGoe) : null;
    }

    /**
     * searchPrice <= item.price
     */
    private BooleanExpression searchPriceLoe(Integer searchPriceLoe) {
        return searchPriceLoe != null ? item.price.loe(searchPriceLoe) : null;
    }
}
