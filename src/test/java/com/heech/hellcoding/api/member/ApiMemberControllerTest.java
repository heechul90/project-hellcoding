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
import com.heech.hellcoding.core.member.dto.UpdateMemberParam;
import com.heech.hellcoding.core.member.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@WebMvcTest(ApiMemberController.class)
class ApiMemberControllerTest {

    //url
    public static final String API_FIND_MEMBERS = "/api/members";
    public static final String API_FIND_MEMBER = "/api/members/{memberId}";
    public static final String API_SAVE_MEMBER = "/api/members";
    public static final String API_UPDATE_MEMBER = "/api/members/{memberId}";
    public static final String API_DELETE_MEMBER = "/api/members/{memberId}";

    //test data
    public static final String NAME = "test_name";
    public static final String LOGIN_ID = "test_loginId";
    public static final String PASSWORD = "test_password";
    public static final String EMAIL = "test_email@mail.com";
    public static final String BIRTH_DATE = "19901009";
    public static final AuthorCode AUTHOR_CODE = AuthorCode.ROLE_USER;
    public static final GenderCode GENDER_CODE = GenderCode.F;
    public static final Mobile MOBILE = new Mobile("010", "4250", "4296");
    public static final Address ADDRESS = new Address("11111", "seoul", "601");

    @Autowired private MockMvc mockMvc;

    @MockBean private MemberService memberService;

    @Autowired ObjectMapper objectMapper;

    private Member getMember(String name, String loginId, String password, String email, String birthDate, AuthorCode authorCode, GenderCode genderCode, Mobile mobile, Address address) {
        return Member.createMemberBuilder()
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
    }

    @Test
    @DisplayName(value = "멤버 목록 조회")
    void findMembersTest() throws Exception {
        //given
        Page<Member> content = Mockito.mock(Page.class);
        ArrayList<Member> members = new ArrayList<>();
        for (int i = 0; i <30; i++) {
            content.getContent().add(getMember(NAME + i, LOGIN_ID + i, PASSWORD + i, EMAIL, BIRTH_DATE, AuthorCode.ROLE_USER, GenderCode.F, MOBILE, ADDRESS));
        }

        given(memberService.findMembers(any(), any())).willReturn(content);

        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setSearchCondition(SearchCondition.NAME);
        condition.setSearchKeyword("test_name1");
        PageRequest pageRequest = PageRequest.of(0, 10);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.get(API_FIND_MEMBERS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.OK.getReasonPhrase()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName(value = "멤버 단건 조회")
    void findMemberTest() throws Exception {
        //given
        Member member = getMember(NAME, LOGIN_ID, PASSWORD, EMAIL, BIRTH_DATE, AuthorCode.ROLE_USER, GenderCode.F, MOBILE, ADDRESS);
        given(memberService.findMember(any())).willReturn(member);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.get(API_FIND_MEMBER, 0L)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.OK.getReasonPhrase()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.memberName").value(NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value(EMAIL))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.birthDate").value(BIRTH_DATE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.author").value(AuthorCode.ROLE_USER.getCodeName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.gender").value(GenderCode.F.getCodeName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.phoneNumber").value(MOBILE.fullPhoneNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.address").value(ADDRESS.fullAddress()))
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

        Member member = getMember(NAME, LOGIN_ID, PASSWORD, EMAIL, BIRTH_DATE, AUTHOR_CODE, GENDER_CODE, MOBILE, ADDRESS);
        given(memberService.saveMember(member)).willReturn(member.getId());

        //expected
        mockMvc.perform(MockMvcRequestBuilders.post(API_SAVE_MEMBER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.OK.getReasonPhrase()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.savedMemberId").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.savedMemberId").isNumber())
                .andDo(MockMvcResultHandlers.print());

        //then

    }

    @Test
    @DisplayName(value = "멤버 수정")
    void updateMemberTest() throws Exception {
        //given
        Member member = getMember(NAME, LOGIN_ID, PASSWORD, EMAIL, BIRTH_DATE, AUTHOR_CODE, GENDER_CODE, MOBILE, ADDRESS);

        UpdateMemberRequest request = new UpdateMemberRequest();
        request.setMemberName("update_name");
        request.setEmail(EMAIL);
        request.setBirthDate("20001009");
        request.setAuthorCode(AUTHOR_CODE);
        request.setGender(GENDER_CODE.name());
        request.setPhoneNumber("01064884296");
        request.setZipcode(ADDRESS.getZipcode());
        request.setAddress(ADDRESS.getAddress());
        request.setDetailAddress(ADDRESS.getDetailAddress());

        given(memberService.findMember(any())).willReturn(member);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.put(API_UPDATE_MEMBER, 0L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andDo(MockMvcResultHandlers.print());

        //then


        //verify
        verify(memberService).updateMember(any(), any());
        verify(memberService, times(1)).updateMember(any(), any());
    }

    @Test
    @DisplayName(value = "멤버 삭제")
    void deleteMemberTest() throws Exception {
        //given
        //Member member = getMember(NAME, LOGIN_ID, PASSWORD, EMAIL, BIRTH_DATE, AUTHOR_CODE, GENDER_CODE, MOBILE, ADDRESS);

        //expected
        mockMvc.perform(MockMvcRequestBuilders.delete(API_DELETE_MEMBER, 0L)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        //then

        //verify
        verify(memberService).deleteMember(any());
        verify(memberService, times(1)).deleteMember(any());
    }
}