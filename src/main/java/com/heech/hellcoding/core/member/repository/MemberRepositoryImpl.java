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

import javax.persistence.EntityManager;

import java.util.List;

import static com.heech.hellcoding.core.member.domain.QMember.*;
import static org.springframework.util.StringUtils.*;

public class MemberRepositoryImpl implements MemberRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Member> findMemberList(MemberSearchCondition condition, Pageable pageable) {
        List<Member> content = getMemberList(condition, pageable);

        JPAQuery<Long> count = getMemberListCount(condition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    /**
     * 회원 목록
     */
    private List<Member> getMemberList(MemberSearchCondition condition, Pageable pageable) {
        List<Member> content = queryFactory
                .select(member)
                .from(member)
                .where(
                        searchCondition(condition.getSearchCondition(), condition.getSearchKeyword()),
                        searchGenderEq(condition.getSearchGender())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return content;
    }

    /**
     * 회원 목록 카운트
     */
    private JPAQuery<Long> getMemberListCount(MemberSearchCondition condition) {
        JPAQuery<Long> count = queryFactory
                .select(member.count())
                .from(member)
                .where(
                        searchCondition(condition.getSearchCondition(), condition.getSearchKeyword()),
                        searchGenderEq(condition.getSearchGender())
                );
        return count;
    }

    private BooleanExpression searchCondition(SearchCondition searchCondition, String searchKeyword) {
        if (searchCondition == null) {
            return null;
        }

        //like : % 내가 추가해야함
        //contains : 앞뒤로 % 추가해서 날라감
        if (SearchCondition.NAME.equals(searchCondition)) {
            return member.name.contains(searchKeyword);
        }
        return null;
    }

    private BooleanExpression searchGenderEq(GenderCode searchGender) {
        return searchGender != null ? member.genderCode.eq(searchGender) : null;
    }

}
