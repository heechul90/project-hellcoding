package com.heech.hellcoding.core.session;

import com.heech.hellcoding.core.member.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest() {
        //세션생성
        MockHttpServletResponse response = new MockHttpServletResponse();
        Member member = Member.createMemberBuilder()
                .name("loginName")
                .loginId("loginId")
                .password("loginPassword")
                .email("loginEmail")
                .build();
        sessionManager.createSession(member, response);

        //요청에 응답 쿠키 저장
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        //세션조회
        Object result = sessionManager.getSession(request);
        assertThat(result).isEqualTo(member);

        //세션만료
        sessionManager.expire(request);
        Object expired = sessionManager.getSession(request);
        assertThat(expired).isNull();
    }
}