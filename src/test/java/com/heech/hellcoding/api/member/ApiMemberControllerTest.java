package com.heech.hellcoding.api.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heech.hellcoding.api.member.request.CreateMemberRequest;
import com.heech.hellcoding.api.member.request.UpdateMemberRequest;
import com.heech.hellcoding.api.member.response.CreateMemberResponse;
import com.heech.hellcoding.core.common.dto.SearchCondition;
import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.common.json.JsonResult;
import com.heech.hellcoding.core.member.domain.AuthorCode;
import com.heech.hellcoding.core.member.domain.GenderCode;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.domain.Mobile;
import com.heech.hellcoding.core.member.dto.MemberSearchCondition;
import com.heech.hellcoding.core.member.service.MemberService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * apiMemberController 단위 테스트
 */
@WebMvcTest(ApiMemberController.class)
class ApiMemberControllerTest {

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
        ArrayList<Member> members = new ArrayList<>();
        for (int i = 0; i <10; i++) {
            members.add(getMember(NAME + i, LOGIN_ID + i, PASSWORD + i, EMAIL, BIRTH_DATE, AUTHOR_CODE, GENDER_CODE, MOBILE, ADDRESS));
        }

        given(memberService.findMembers(any(MemberSearchCondition.class), any(PageRequest.class))).willReturn(new PageImpl(members));

        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setSearchCondition(SearchCondition.NAME);
        condition.setSearchKeyword("test_name1");
        PageRequest pageRequest = PageRequest.of(0, 10);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(API_FIND_MEMBERS)
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", String.valueOf(pageRequest.getOffset()))
                .param("size", String.valueOf(pageRequest.getPageSize()))
                .param("searchCondition", condition.getSearchCondition().name())
                .param("searchKeyword", condition.getSearchKeyword())
        );

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.OK.getReasonPhrase()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()", Matchers.is(10)))
                .andDo(MockMvcResultHandlers.print());

        //verify
        verify(memberService).findMembers(any(), any());
        verify(memberService, times(1)).findMembers(any(), any());
    }

    @Test
    @DisplayName(value = "멤버 단건 조회")
    void findMemberTest() throws Exception {
        //given
        Member member = getMember(NAME, LOGIN_ID, PASSWORD, EMAIL, BIRTH_DATE, AUTHOR_CODE, GENDER_CODE, MOBILE, ADDRESS);
        given(memberService.findMember(any())).willReturn(member);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(API_FIND_MEMBER, 0L)
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.OK.getReasonPhrase()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.memberName").value(NAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value(EMAIL))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.birthDate").value(BIRTH_DATE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.author").value(AuthorCode.ROLE_USER.getCodeName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.gender").value(GenderCode.F.getCodeName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.phoneNumber").value(MOBILE.fullPhoneNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.address").value(ADDRESS.fullAddress()))
                .andDo(MockMvcResultHandlers.print());

        //verify
        verify(memberService).findMember(any());
        verify(memberService, times(1)).findMember(any());
    }

    @Test
    @DisplayName(value = "멤버 저장")
    void saveMemberTest() throws Exception {
        //given
        Member member = getMember(NAME, LOGIN_ID, PASSWORD, EMAIL, BIRTH_DATE, AUTHOR_CODE, GENDER_CODE, MOBILE, ADDRESS);
        given(memberService.saveMember(member)).willReturn(member);


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
                .content(objectMapper.writeValueAsString(request))
        );

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.OK.getReasonPhrase()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.savedMemberId").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.savedMemberId").isNumber())
                .andDo(MockMvcResultHandlers.print());

        //verify
        verify(memberService).saveMember(any());
        verify(memberService, times(1)).saveMember(any());
    }

    @Test
    @DisplayName(value = "멤버 수정")
    void updateMemberTest() throws Exception {
        //given
        Member member = getMember(NAME, LOGIN_ID, PASSWORD, EMAIL, BIRTH_DATE, AUTHOR_CODE, GENDER_CODE, MOBILE, ADDRESS);
        given(memberService.findMember(any())).willReturn(member);

        UpdateMemberRequest request = new UpdateMemberRequest();
        request.setMemberName(UPDATE_NAME);
        request.setEmail(UPDATE_EMAIL);
        request.setBirthDate(UPDATE_BIRTH_DATE);
        request.setAuthorCode(AUTHOR_CODE);
        request.setGender(GENDER_CODE.name());
        request.setPhoneNumber(UPDATE_PHONE_NUMBER);
        request.setZipcode(ADDRESS.getZipcode());
        request.setAddress(ADDRESS.getAddress());
        request.setDetailAddress(ADDRESS.getDetailAddress());


        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put(API_UPDATE_MEMBER, 0L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andDo(MockMvcResultHandlers.print());

        //verify
        verify(memberService).updateMember(any(), any());
        verify(memberService, times(1)).updateMember(any(), any());
        verify(memberService).findMember(any());
        verify(memberService, times(1)).findMember(any());
    }

    @Test
    @DisplayName(value = "멤버 삭제")
    void deleteMemberTest() throws Exception {
        //given
        //Member member = getMember(NAME, LOGIN_ID, PASSWORD, EMAIL, BIRTH_DATE, AUTHOR_CODE, GENDER_CODE, MOBILE, ADDRESS);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete(API_DELETE_MEMBER, 0L)
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        //verify
        verify(memberService).deleteMember(any());
        verify(memberService, times(1)).deleteMember(any());
    }
}