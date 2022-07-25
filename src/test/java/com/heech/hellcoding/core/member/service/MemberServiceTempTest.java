package com.heech.hellcoding.core.member.service;

import com.heech.hellcoding.api.member.request.UpdateMemberRequest;
import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.member.domain.AuthorCode;
import com.heech.hellcoding.core.member.domain.GenderCode;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.domain.Mobile;
import com.heech.hellcoding.core.member.dto.MemberSearchCondition;
import com.heech.hellcoding.core.member.dto.UpdateMemberParam;
import com.heech.hellcoding.core.member.repository.MemberQueryRepository;
import com.heech.hellcoding.core.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceTempTest {

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

    @InjectMocks MemberService memberService;

    @Mock MemberRepository memberRepository;

    @Mock MemberQueryRepository memberQueryRepository;

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
    void findMembersTest() {
        //given
        ArrayList<Member> members = new ArrayList<>();
        for (int i = 0; i <10; i++) {
            members.add(getMember(NAME + i, LOGIN_ID + i, PASSWORD + i, EMAIL, BIRTH_DATE, AUTHOR_CODE, GENDER_CODE, MOBILE, ADDRESS));
        }
        given(memberQueryRepository.findMembers(any(), any())).willReturn(new PageImpl(members));

        //when
        Page<Member> content = memberService.findMembers(any(MemberSearchCondition.class), any(PageRequest.class));

        //then
        assertThat(content.getTotalElements()).isEqualTo(10);
        assertThat(content.getContent().size()).isEqualTo(10);
        assertThat(content.getContent()).extracting("name").contains(NAME + 1, NAME + 9);
    }

    @Test
    void findMemberTest() {
        //given
        Member member = getMember(NAME, LOGIN_ID, PASSWORD, EMAIL, BIRTH_DATE, AUTHOR_CODE, GENDER_CODE, MOBILE, ADDRESS);
        given(memberRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(member));

        //when
        Member findMember = memberService.findMember(any(Long.class));

        //then
        assertThat(findMember.getName()).isEqualTo(NAME);
        assertThat(findMember.getLoginId()).isEqualTo(LOGIN_ID);
        assertThat(findMember.getPassword()).isEqualTo(PASSWORD);
        assertThat(findMember.getEmail()).isEqualTo(EMAIL);
        assertThat(findMember.getBirthDate()).isEqualTo(BIRTH_DATE);
        assertThat(findMember.getAuthorCode()).isEqualTo(AUTHOR_CODE);
        assertThat(findMember.getGenderCode()).isEqualTo(GENDER_CODE);
        assertThat(findMember.getMobile()).isEqualTo(MOBILE);
        assertThat(findMember.getAddress()).isEqualTo(ADDRESS);
    }

    @Test
    void findMemberTest_validation() {
        //given
        Member member = getMember(NAME, LOGIN_ID, PASSWORD, EMAIL, BIRTH_DATE, AUTHOR_CODE, GENDER_CODE, MOBILE, ADDRESS);
        //given(memberRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(member));

        //when

        //then
    }

    @Test
    public void saveMemberTest() {
        //given
        Member member = getMember(NAME, LOGIN_ID, PASSWORD, EMAIL, BIRTH_DATE, AUTHOR_CODE, GENDER_CODE, MOBILE, ADDRESS);
        given(memberRepository.save(any())).willReturn(member);

        //when
        Member savedMember = memberService.saveMember(member);

        //then
        assertThat(savedMember.getName()).isEqualTo(NAME);
        assertThat(savedMember.getLoginId()).isEqualTo(LOGIN_ID);
        assertThat(savedMember.getPassword()).isEqualTo(PASSWORD);
        assertThat(savedMember.getEmail()).isEqualTo(EMAIL);
        assertThat(savedMember.getBirthDate()).isEqualTo(BIRTH_DATE);
        assertThat(savedMember.getAuthorCode()).isEqualTo(AUTHOR_CODE);
        assertThat(savedMember.getGenderCode()).isEqualTo(GENDER_CODE);
        assertThat(savedMember.getMobile().fullPhoneNumber()).isEqualTo(MOBILE.fullPhoneNumber());
        assertThat(savedMember.getAddress().fullAddress()).isEqualTo(ADDRESS.fullAddress());
    }

    @Test
    public void updateMemberTest() throws Exception{
        //given
        Member member = getMember(NAME, LOGIN_ID, PASSWORD, EMAIL, BIRTH_DATE, AUTHOR_CODE, GENDER_CODE, MOBILE, ADDRESS);
        given(memberRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(member));
        Member findMember = memberService.findMember(any(Long.class));

        //when
        UpdateMemberParam param = UpdateMemberParam.builder()
                .name("update" + NAME)
                .email("update" + EMAIL)
                .birthDate(BIRTH_DATE)
                .authorCode(AUTHOR_CODE)
                .genderCode(GENDER_CODE)
                .mobile(MOBILE)
                .address(ADDRESS)
                .build();
        memberService.updateMember(any(), param);

        //then
    }
}