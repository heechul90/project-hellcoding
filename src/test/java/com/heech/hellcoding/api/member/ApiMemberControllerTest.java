package com.heech.hellcoding.api.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heech.hellcoding.core.category.domain.Category;
import com.heech.hellcoding.core.category.domain.ServiceName;
import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.member.domain.GenderCode;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.domain.Mobile;
import com.heech.hellcoding.core.member.dto.MemberSearchCondition;
import com.heech.hellcoding.core.member.service.MemberService;
import lombok.Setter;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@WebMvcTest(ApiMemberController.class)
class ApiMemberControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private MemberService memberService;

    @Autowired ObjectMapper objectMapper;

    private Long getMember(String name, String loginId, String password, String email) {
        Long savedMemberId = memberService.saveMember(
                Member.createMemberBuilder()
                        .name(name)
                        .loginId(loginId)
                        .password(password)
                        .email(email)
                        .build()
        );
        return savedMemberId;
    }

    /*private Long getMember(String name, String loginId, String password, String email) {
        Member member = Member.createMemberBuilder()
                .name(name)
                .loginId(loginId)
                .password(password)
                .email(email)
                .build();
        em.persist(member);
        return member.getId();
    }*/

    /*private void getMembers() {
        for (int i = 0; i < 30; i++) {
            memberService.saveMember(Member.createMemberBuilder()
                    .name("test_name" + i)
                    .loginId("test_loginId" + i)
                    .password("test_password" + i)
                    .email("test_email" + i + "@mail.com")
                    .build()
            );
        }
    }*/

    @Test
    void findMembersTest() throws Exception {
        //given
        //getMembers();

        MemberSearchCondition condition = new MemberSearchCondition();
        PageRequest pageRequest = PageRequest.of(0, 10);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.get("/api/members?page=0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void findMemberTest() throws Exception {
        //given
        Member member = Member.createMemberBuilder()
                .name("name")
                .loginId("loginId")
                .password("password")
                .email("email")
                .birthDate("19901009")
                .mobile(new Mobile("010", "4250", "4296"))
                .address(new Address("11111", "seoul", "101ho"))
                .build();
        given(memberService.findMember(any())).willReturn(member);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.get("/api/members/{memberId}", 1L))
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