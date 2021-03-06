package com.heech.hellcoding.api.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heech.hellcoding.api.member.request.CreateMemberRequest;
import com.heech.hellcoding.api.member.request.UpdateMemberRequest;
import com.heech.hellcoding.core.common.dto.SearchCondition;
import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.member.domain.AuthorCode;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * apiMemberController 통합 테스트
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ApiMemberControllerIntegrationTest {

    //REQUEST_URL
    public static final String API_FIND_MEMBERS = "/api/members";
    public static final String API_FIND_MEMBER = "/api/members/{memberId}";
    public static final String API_SAVE_MEMBER = "/api/members";
    public static final String API_UPDATE_MEMBER = "/api/members/{memberId}";
    public static final String API_DELETE_MEMBER = "/api/members/{memberId}";

    //MEMBER_DATA
    public static final String NAME = "test_name";
    public static final String LOGIN_ID = "test_loginId";
    public static final String PASSWORD = "test_password";
    public static final String EMAIL = "test_email@mail.com";
    public static final String BIRTH_DATE = "19901009";
    public static final AuthorCode AUTHOR_CODE = AuthorCode.ROLE_USER;
    public static final GenderCode GENDER_CODE = GenderCode.F;
    public static final Mobile MOBILE = new Mobile("010", "4250", "4296");
    public static final Address ADDRESS = new Address("11111", "seoul", "601");

    //UPDATE_MEMBER
    public static final String UPDATE_NAME = "update_" + NAME;
    public static final String UPDATE_EMAIL = "update_" + EMAIL;
    public static final String UPDATE_BIRTH_DATE = "20001009";
    public static final String UPDATE_PHONE_NUMBER = "01064884296";

    @PersistenceContext EntityManager em;

    @Autowired MockMvc mockMvc;

    @Autowired ObjectMapper objectMapper;

    @Autowired MemberService memberService;

    private Member getMember(String name, String loginId, String password, String email, String birthDate, AuthorCode authorCode, GenderCode genderCode, Mobile mobile, Address address) {
        Member member = Member.createMemberBuilder()
                .name(name)
                .loginId(loginId)
                .password(password)
                .email(email)
                .birthDate(birthDate)
                .authorCode(authorCode)
                .genderCode(genderCode)
                .mobile(mobile)
                .address(address)
                .build();
        return member;
    }

    @Test
    @DisplayName(value = "멤버 목록 조회")
    void findMembersTest() throws Exception {
        //given
        for (int i = 0; i < 30; i++) {
            Member member = getMember(NAME + i, LOGIN_ID + i, PASSWORD + i, EMAIL, BIRTH_DATE, AUTHOR_CODE, GENDER_CODE, MOBILE, ADDRESS);
            memberService.saveMember(member);
        }

        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setSearchCondition(SearchCondition.NAME);
        condition.setSearchKeyword(NAME + 1);
        condition.setSearchGender(GenderCode.F);

        PageRequest pageRequest = PageRequest.of(0, 10);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(API_FIND_MEMBERS)
                .contentType(MediaType.APPLICATION_JSON)
                .param("searchCondition", condition.getSearchCondition().name())
                .param("searchKeyword", condition.getSearchKeyword())
                .param("page", String.valueOf(pageRequest.getOffset()))
                .param("size", String.valueOf(pageRequest.getPageSize())));

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.OK.getReasonPhrase()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()", Matchers.is(10)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName(value = "멤버 단건 조회")
    void findMemberTest() throws Exception {
        //given
        Member member = getMember(NAME, LOGIN_ID, PASSWORD, EMAIL, BIRTH_DATE, AUTHOR_CODE, GENDER_CODE, MOBILE, ADDRESS);
        Member savedMember = memberService.saveMember(member);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(API_FIND_MEMBER, savedMember.getId())
                .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.OK.getReasonPhrase()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.memberId").value(member.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.memberName").value(NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value(EMAIL))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.birthDate").value(BIRTH_DATE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.author").value(AUTHOR_CODE.getCodeName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.gender").value(GENDER_CODE.getCodeName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.phoneNumber").value(MOBILE.fullPhoneNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.address").value(ADDRESS.fullAddress()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName(value = "멤버 단건 조회_예외 발생")
    void findMemberTest_validation() throws Exception {
        //given
        Member member = getMember(NAME, LOGIN_ID, PASSWORD, EMAIL, BIRTH_DATE, AUTHOR_CODE, GENDER_CODE, MOBILE, ADDRESS);
        Member savedMember = memberService.saveMember(member);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(API_FIND_MEMBER, savedMember.getId() + 1L)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.NOT_FOUND.name()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName(value = "멤버 저장")
    void saveMemberTest() throws Exception {
        //given
        CreateMemberRequest request = new CreateMemberRequest();
        request.setMemberName(NAME);
        request.setLoginId(LOGIN_ID);
        request.setPassword(PASSWORD);
        request.setEmail(EMAIL);
        request.setBirthDate(BIRTH_DATE);
        request.setAuthorCode(AUTHOR_CODE);
        request.setGender(GENDER_CODE.name());
        request.setPhoneNumber(MOBILE.getMobileNumberFirst() + MOBILE.getMobileNumberMiddle() + MOBILE.getMobileNumberLast());
        request.setZipcode(ADDRESS.getZipcode());
        request.setAddress(ADDRESS.getAddress());
        request.setDetailAddress(ADDRESS.getDetailAddress());

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(API_SAVE_MEMBER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.OK.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName(value = "멤버 수정")
    void updateMemberTest() throws Exception {
        //given
        Member member = getMember(NAME, LOGIN_ID, PASSWORD, EMAIL, BIRTH_DATE, AUTHOR_CODE, GENDER_CODE, MOBILE, ADDRESS);
        Member savedMember = memberService.saveMember(member);

        UpdateMemberRequest request = new UpdateMemberRequest();
        request.setMemberName(UPDATE_NAME);
        request.setEmail(UPDATE_EMAIL);
        request.setBirthDate(UPDATE_BIRTH_DATE);
        request.setPhoneNumber(UPDATE_PHONE_NUMBER);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put(API_UPDATE_MEMBER, savedMember.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.OK.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.updatedMemberId").value(savedMember.getId()))
                .andDo(MockMvcResultHandlers.print());
        Member updatedMember = memberService.findMember(savedMember.getId());
        assertThat(updatedMember.getName()).isEqualTo(UPDATE_NAME);
        assertThat(updatedMember.getEmail()).isEqualTo(UPDATE_EMAIL);
        assertThat(updatedMember.getBirthDate()).isEqualTo(UPDATE_BIRTH_DATE);
        assertThat(updatedMember.getAuthorCode()).isEqualTo(AUTHOR_CODE);
        assertThat(updatedMember.getGenderCode()).isEqualTo(GENDER_CODE);
        assertThat(updatedMember.getMobile().getMobileNumberFirst()
                + updatedMember.getMobile().getMobileNumberMiddle()
                + updatedMember.getMobile().getMobileNumberLast())
                .isEqualTo(UPDATE_PHONE_NUMBER);
        assertThat(updatedMember.getAddress().fullAddress()).isEqualTo(ADDRESS.fullAddress());
    }

    @Test
    @DisplayName(value = "멤버 삭제")
    void deleteMemberTest() throws Exception {
        //given
        Member member = getMember(NAME, LOGIN_ID, PASSWORD, EMAIL, BIRTH_DATE, AUTHOR_CODE, GENDER_CODE, MOBILE, ADDRESS);
        Member savedMember = memberService.saveMember(member);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete(API_DELETE_MEMBER, savedMember.getId())
                .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
