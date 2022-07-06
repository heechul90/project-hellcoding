package com.heech.hellcoding.core.user.info.service;

import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InfoService {

    private final MemberRepository memberRepository;

    /**
     * 멤버 조회
     */
    public Member findMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("잘못된 접근입니다."));
    }
}
