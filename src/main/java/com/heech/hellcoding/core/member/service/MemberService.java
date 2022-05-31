package com.heech.hellcoding.core.member.service;

import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.dto.MemberSearchCondition;
import com.heech.hellcoding.core.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.StringUtils.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 목록
     */
    public Page<Member> findMembers(MemberSearchCondition condition, Pageable pageable) {
        return memberRepository.findMembers(condition, pageable);
    }

    /**
     * 회원 저장
     */
    @Transactional
    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    /**
     * 회원 수정
     */
    @Transactional
    public void updateMember(Long id, String memberName, String email, String password) {
        Member findMember = memberRepository.findById(id).orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));
        System.out.println("password = " + password);
        if (hasText(memberName)) {
            findMember.changeMemberName(memberName);
        }
        if (hasText(email)) {
            findMember.changeEmail(email);
        }
        if (hasText(password)) {
            findMember.changePassword(password);
        }
    }

    /**
     * 회원 삭제
     */
    @Transactional
    public void deleteMember(Long id) {
        Member findMember = memberRepository.findById(id).orElseGet(null);
        memberRepository.delete(findMember);
        System.out.println("findMember.getPassword() = " + findMember.getPassword());
    }

    /**
     * 회원 조회 By Id
     */
    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

}
