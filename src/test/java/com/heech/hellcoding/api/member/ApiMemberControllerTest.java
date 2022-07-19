package com.heech.hellcoding.api.member;

import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@WebMvcTest(ApiMemberController.class)
@AutoConfigureMockMvc
@Transactional
class ApiMemberControllerTest {

    @PersistenceContext private EntityManager em;

    @Autowired private MockMvc mockMvc;

    @MockBean private MemberService memberService;

    @Test
    void findMembersTest() {
    }

    @Test
    void findMemberTest() throws Exception {
        //given
        Member member = Member.createMemberBuilder()
                .name("test")
                .loginId("test")
                .password("test")
                .email("test")
                .build();
        em.persist(member);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/members/{id}", member.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void saveMemberTest() {
    }

    @Test
    void updateMemberTest() {
    }

    @Test
    void deleteMemberTest() {
    }
}