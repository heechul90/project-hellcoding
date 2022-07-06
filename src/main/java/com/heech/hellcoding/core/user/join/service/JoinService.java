package com.heech.hellcoding.core.user.join.service;

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
public class JoinService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    @Transactional
    public Member save(Member member) {
        return memberRepository.save(member);
    }

}
