package com.heech.hellcoding.core.user.login.service;

import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.repository.MemberRepository;
import com.heech.hellcoding.core.user.UserTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(UserTestConfig.class)
class LoginServiceTest {

    @PersistenceContext
    EntityManager em;

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
        em.persist(member);
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