package com.heech.hellcoding.user.sign.service;

import com.heech.hellcoding.member.domain.Member;
import com.heech.hellcoding.member.repository.MemberRepository;
import com.heech.hellcoding.member.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoginServiceTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    private final LoginService loginService;

    @Autowired
    public LoginServiceTest(LoginService loginService) {
        this.loginService = loginService;
    }

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    void loginSuccess() {
        //given
        Member member = new Member();
        member.setLoginId("springId");
        member.setName("springName");
        member.setPassword("springPassword");

        Member savedMember = repository.save(member);

        //when
        Member loginMember = loginService.login(savedMember.getLoginId(), savedMember.getPassword());

        //then
        assertThat(loginMember).isEqualTo(savedMember);
    }

    @Test
    void loginFali() {
        //given
        Member member = new Member();
        member.setLoginId("springId");
        member.setName("springName");
        member.setPassword("springPassword");

        Member savedMember = repository.save(member);

        //when
        Member loginMember = loginService.login("springid", "springpassword");

        //then
        assertThat(loginMember).isNull();

    }
}