package com.heech.hellcoding.api.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heech.hellcoding.api.member.request.CreateMemberRequest;
import com.heech.hellcoding.api.member.request.UpdateMemberRequest;
import com.heech.hellcoding.core.common.dto.SearchCondition;
import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import com.heech.hellcoding.core.member.domain.GenderCode;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.domain.Mobile;
import com.heech.hellcoding.core.member.dto.MemberSearchCondition;
import com.heech.hellcoding.core.member.service.MemberService;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.*;

//@WebMvcTest(ApiMemberController.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ApiMemberControllerTest {

    @PersistenceContext EntityManager em;

    @Autowired private MockMvc mockMvc;

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
    @DisplayName(value = "멤버 목록 조회")
    void findMembersTest() throws Exception {
        //given
        getMembers();

        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setSearchCondition(SearchCondition.NAME);
        condition.setSearchKeyword("test_name1");
        PageRequest pageRequest = PageRequest.of(0, 10);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.get("/api/members")
                        .param("page", String.valueOf(pageRequest.getOffset()))
                        .param("size", String.valueOf(pageRequest.getPageSize()))
                        .param("searchCondition", condition.getSearchCondition().toString())
                        .param("searchKeyword", condition.getSearchKeyword())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()", Matchers.is(10)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName(value = "멤버 단건 조회")
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
    @DisplayName(value = "멤버 저장")
    void saveMemberTest() throws Exception {
        //given
        CreateMemberRequest request = new CreateMemberRequest();
        request.setMemberName("test_name");
        request.setLoginId("test_loginId");
        request.setPassword("test_password");
        request.setEmail("test_email@mail.com");
        request.setBirthDate("19901009");
        request.setGender("M");
        request.setPhoneNumber("01042504296");
        request.setZipcode("11111");
        request.setAddress("seoul");
        request.setDetailAddress("101");

        //expected
        mockMvc.perform(MockMvcRequestBuilders.post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.savedMemberId").isNumber())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName(value = "멤버 수정")
    void updateMemberTest() throws Exception {
        Long savedMemberId = getMember("test_name", "test_loginId", "test_password", "test_email@mail.com", "19901009", GenderCode.M);

        UpdateMemberRequest request = new UpdateMemberRequest();
        request.setMemberName("update_name");
        request.setEmail("");
        request.setBirthDate("20001009");
        request.setGender("F");
        request.setPhoneNumber("01122223333");

        //expected
        mockMvc.perform(MockMvcRequestBuilders.put("/api/members/{memberId}", savedMemberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.updatedMemberId").isNumber())
                .andDo(MockMvcResultHandlers.print());

        //then
        Member findMember = em.find(Member.class, savedMemberId);
        assertThat(findMember.getName()).isEqualTo("update_name");
        assertThat(findMember.getLoginId()).isEqualTo("test_loginId");
        assertThat(findMember.getPassword()).isEqualTo("test_password");
        assertThat(findMember.getEmail()).isEqualTo("test_email@mail.com");
        assertThat(findMember.getBirthDate()).isEqualTo("20001009");
        assertThat(findMember.getGenderCode()).isEqualTo(GenderCode.F);
        assertThat(findMember.getMobile().getMobileNumberFirst()).isEqualTo("011");
        assertThat(findMember.getMobile().getMobileNumberMiddle()).isEqualTo("2222");
        assertThat(findMember.getMobile().getMobileNumberLast()).isEqualTo("3333");
        assertThat(findMember.getAddress().getZipcode()).isEqualTo("11111");
        assertThat(findMember.getAddress().getAddress()).isEqualTo("seoul");
        assertThat(findMember.getAddress().getDetailAddress()).isEqualTo("601");
    }

    @Test
    @DisplayName(value = "멤버 삭제")
    void deleteMemberTest() throws Exception {
        //given
        Long savedMemberId = getMember("test_name", "test_loginId", "test_password", "test_email@mail.com", "19901009", GenderCode.M);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/members/{memberId}", savedMemberId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}