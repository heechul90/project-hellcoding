package com.heech.hellcoding.core.user;

import com.heech.hellcoding.core.member.repository.MemberRepository;
import com.heech.hellcoding.core.user.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class UserTestConfig {

    @Autowired MemberRepository memberRepository;

    @Bean
    public LoginService loginService() {
        return new LoginService(memberRepository);
    }
}
