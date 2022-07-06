package com.heech.hellcoding.core.user.login.service;

import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LoginServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    LoginService loginService;

    @Test
    void login() {
        //given
        Member member = Member.createMemberBuilder()
                .name("test_name")
                .loginId("test_loginId")
                .password("test_password")
                .email("test_email@spring.com")
                .build();
        Member savedMember = memberRepository.save(member);
        em.flush();
        em.clear();

        //when
        Member loginMember = loginService.login(member.getLoginId(), member.getPassword());
        Member loginFailMember = loginService.login("test_loginId1", "test_password");

        //then
        assertThat(loginMember.getLoginId()).isEqualTo("test_loginId");
        assertThat(loginMember.getName()).isEqualTo("test_name");
        assertThat(loginMember.getPassword()).isEqualTo("test_password");
        assertThat(loginFailMember).isNull();
    }
}