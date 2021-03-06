package com.heech.hellcoding.core.member.service;

import com.heech.hellcoding.core.common.exception.EntityNotFound;
import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.dto.MemberSearchCondition;
import com.heech.hellcoding.core.member.dto.UpdateMemberParam;
import com.heech.hellcoding.core.member.repository.MemberQueryRepository;
import com.heech.hellcoding.core.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;

    /**
     * 회원 목록
     */
    public Page<Member> findMembers(MemberSearchCondition condition, Pageable pageable) {
        return memberQueryRepository.findMembers(condition, pageable);
    }

    /**
     * 회원 조회 By Id
     */
    public Member findMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Post", id));
    }

    /**
     * 회원 저장
     * @return
     */
    @Transactional
    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    /**
     * 회원 수정
     */
    @Transactional
    public void updateMember(Long id, UpdateMemberParam param) {
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Post", id));
        findMember.updateMemberBuilder()
                .param(param)
                .build();
    }

    /**
     * 회원 삭제
     */
    @Transactional
    public void deleteMember(Long id) {
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Post", id));
        memberRepository.delete(findMember);
    }

}
