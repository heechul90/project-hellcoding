package com.heech.hellcoding.api.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.member.domain.GenderCode;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.domain.Mobile;
import com.heech.hellcoding.core.member.service.MemberService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//@WebMvcTest(ApiMemberController.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ApiMemberControllerTest {

    @PersistenceContext EntityManager em;

    @Autowired private MockMvc mockMvc;

    @Autowired private MemberService memberService;

    @Autowired ObjectMapper objectMapper;

    private Long getMember(String name, String loginId, String password, String email, String birthDate, GenderCode genderCode) {
        Member member = Member.createMemberBuilder()
                .name(name)
                .loginId(loginId)
                .password(password)
                .email(email)
                .birthDate(birthDate)
                .genderCode(genderCode)
                .mobile(new Mobile("010", "4250", "4296"))
                .address(new Address("11111", "seoul", "601"))
                .build();
        em.persist(member);
        return member.getId();
    }

    private void getMembers() {
        for (int i = 0; i < 30; i++) {
            em.persist(Member.createMemberBuilder()
                    .name("test_name" + i)
                    .loginId("test_loginId" + i)
                    .password("test_password" + i)
                    .email("test_email" + i + "@mail.com")
                    .birthDate("19901009")
                    .genderCode(GenderCode.M)
                    .mobile(new Mobile("010", "4250", "4296"))
                    .address(new Address("11111", "seoul", "601"))
                    .build()
            );
        }
    }

    @Test
    void findMembersTest() throws Exception {
        //given
        getMembers();

        //expected
        mockMvc.perform(MockMvcRequestBuilders.get("/api/members")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()", Matchers.is(10)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void findMemberTest() throws Exception {
        //given
        Long savedMemberId = getMember("test_name", "test_loginId", "test_password", "test_email", "19901009", GenderCode.M);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.get("/api/members/{memberId}", savedMemberId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.memberName").value("test_name"))
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