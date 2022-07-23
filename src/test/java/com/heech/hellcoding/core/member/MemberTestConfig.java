package com.heech.hellcoding.core.member;

import com.heech.hellcoding.api.member.ApiMemberController;
import com.heech.hellcoding.core.member.repository.MemberQueryRepository;
import com.heech.hellcoding.core.member.repository.MemberRepository;
import com.heech.hellcoding.core.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@TestConfiguration
public class MemberTestConfig {

    @PersistenceContext EntityManager em;

    @Autowired MemberRepository memberRepository;

    @Bean
    public MemberQueryRepository memberQueryRepository() {
        return new MemberQueryRepository(em);
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository, memberQueryRepository());
    }

    //@Bean
    public ApiMemberController apiMemberController() {
        return new ApiMemberController(memberService());
    }
}
