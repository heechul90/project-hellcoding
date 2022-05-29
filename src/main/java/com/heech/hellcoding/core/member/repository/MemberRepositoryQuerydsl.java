package com.heech.hellcoding.core.member.repository;

import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.dto.MemberSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryQuerydsl {

    Page<Member> findMemberList(MemberSearchCondition condition, Pageable pageable);
}
