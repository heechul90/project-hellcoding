package com.heech.hellcoding.core.member.service;

import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 목록
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    @Transactional
    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    @Transactional
    public void updateMmeber(Long id, String password) {
        Member findMember = memberRepository.findById(id).orElseGet(null);
        findMember.changePassword(password);
    }

    @Transactional
    public void deleteMember(Long id) {
        Member findMember = memberRepository.findById(id).orElseGet(null);
        memberRepository.delete(findMember);
        System.out.println("findMember.getPassword() = " + findMember.getPassword());
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

}
