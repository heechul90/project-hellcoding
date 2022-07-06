package com.heech.hellcoding.core.member.repository;

import com.heech.hellcoding.core.common.dto.SearchCondition;
import com.heech.hellcoding.core.member.domain.GenderCode;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.dto.MemberSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.heech.hellcoding.core.member.domain.QMember.*;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    public MemberQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 회원 목록 조회
     */
    public Page<Member> findMembers(MemberSearchCondition condition, Pageable pageable) {
        List<Member> content = getMemberList(condition, pageable);

        JPAQuery<Long> count = getMemberListCount(condition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    /**
     * 회원 목록
     */
    private List<Member> getMemberList(MemberSearchCondition condition, Pageable pageable) {
        return queryFactory
                .select(member)
                .from(member)
                .where(
                        searchCondition(condition.getSearchCondition(), condition.getSearchKeyword()),
                        searchGenderEq(condition.getSearchGender())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    /**
     * 회원 목록 카운트
     */
    private JPAQuery<Long> getMemberListCount(MemberSearchCondition condition) {
        return queryFactory
                .select(member.count())
                .from(member)
                .where(
                        searchCondition(condition.getSearchCondition(), condition.getSearchKeyword()),
                        searchGenderEq(condition.getSearchGender())
                );
    }

    private BooleanExpression searchCondition(SearchCondition searchCondition, String searchKeyword) {
        if (searchCondition == null || !hasText(searchKeyword)) {
            return null;
        }

        if (SearchCondition.NAME.equals(searchCondition)) {
            return member.name.contains(searchKeyword);
        } else {
            return null;
        }

    }

    /**
     * searchGender == member.genderCode
     * @param searchGender
     * @return
     */
    private BooleanExpression searchGenderEq(GenderCode searchGender) {
        return searchGender != null ? member.genderCode.eq(searchGender) : null;
    }

}
