package com.heech.hellcoding.core.member.service;

import com.heech.hellcoding.core.common.dto.SearchCondition;
import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.common.exception.EntityNotFound;
import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import com.heech.hellcoding.core.member.domain.AuthorCode;
import com.heech.hellcoding.core.member.domain.GenderCode;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.domain.Mobile;
import com.heech.hellcoding.core.member.dto.MemberSearchCondition;
import com.heech.hellcoding.core.member.dto.UpdateMemberParam;
import com.heech.hellcoding.core.member.repository.MemberQueryRepository;
import com.heech.hellcoding.core.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

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

    //UPDATE_MEMBER_DATA
    public static final String UPDATE_NAME = "update_" + NAME;
    public static final String UPDATE_EMAIL = "update_" + EMAIL;

    //VALIDATION_MESSAGE
    public static final String HAS_MESSAGE_STARTING_WITH = "존재하지 않는 Post";

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
    @DisplayName(value = "멤버 목록 조회")
    void findMembersTest() {
        //given
        ArrayList<Member> members = new ArrayList<>();
        for (int i = 0; i <10; i++) {
            members.add(getMember(NAME + i, LOGIN_ID + i, PASSWORD + i, EMAIL, BIRTH_DATE, AUTHOR_CODE, GENDER_CODE, MOBILE, ADDRESS));
        }
        given(memberQueryRepository.findMembers(any(MemberSearchCondition.class), any())).willReturn(new PageImpl(members));

        //when

        //TODO 검색 작동 안함.
        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setSearchCondition(SearchCondition.NAME);
        condition.setSearchKeyword(NAME + 1);
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<Member> content = memberService.findMembers(condition, pageRequest);

        //then
        assertThat(content.getTotalElements()).isEqualTo(10);
        assertThat(content.getContent().size()).isEqualTo(10);
        assertThat(content.getContent()).extracting("name").contains(NAME + 1, NAME + 9);

        //verify
        verify(memberQueryRepository).findMembers(any(), any());
        verify(memberQueryRepository, times(1)).findMembers(any(), any());
    }

    @Test
    @DisplayName(value = "멤버 단건 조회")
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

        //verify
        verify(memberRepository).findById(any(Long.class));
        verify(memberRepository, times(1)).findById(any(Long.class));
    }

    @Test
    @DisplayName(value = "멤버 단건 조회_예외 발생")
    void findMemberTest_validation() {
        //given
        Member member = getMember(NAME, LOGIN_ID, PASSWORD, EMAIL, BIRTH_DATE, AUTHOR_CODE, GENDER_CODE, MOBILE, ADDRESS);
        //given(memberRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(member));

        //expected
        assertThatThrownBy(() -> memberService.findMember(0L))
                .isInstanceOf(EntityNotFound.class)
                .hasMessageStartingWith(HAS_MESSAGE_STARTING_WITH)
                .hasMessageEndingWith("id = " + 0);

        //verify
        verify(memberRepository).findById(any(Long.class));
        verify(memberRepository, times(1)).findById(any(Long.class));
    }

    @Test
    @DisplayName(value = "멤버 저장")
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
    @DisplayName(value = "멤버 수정")
    void updateMemberTest() {
        //given
        Member member = getMember(NAME, LOGIN_ID, PASSWORD, EMAIL, BIRTH_DATE, AUTHOR_CODE, GENDER_CODE, MOBILE, ADDRESS);
        given(memberRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(member));

        UpdateMemberParam param = UpdateMemberParam.builder()
                .name(UPDATE_NAME)
                .email(UPDATE_EMAIL)
                .birthDate(BIRTH_DATE)
                .authorCode(AUTHOR_CODE)
                .genderCode(GENDER_CODE)
                .mobile(MOBILE)
                .address(ADDRESS)
                .build();

        //when
        memberService.updateMember(any(Long.class), param);

        //then
        assertThatThrownBy(() -> memberService.updateMember(member.getId(), param))
                .isInstanceOf(EntityNotFound.class)
                .hasMessageStartingWith(HAS_MESSAGE_STARTING_WITH)
                .hasMessageContaining("id = " + member.getId());
        assertThat(member.getName()).isEqualTo(UPDATE_NAME);
        assertThat(member.getEmail()).isEqualTo(UPDATE_EMAIL);
        assertThat(member.getBirthDate()).isEqualTo(BIRTH_DATE);
        assertThat(member.getAuthorCode()).isEqualTo(AUTHOR_CODE);
        assertThat(member.getGenderCode()).isEqualTo(GENDER_CODE);
        assertThat(member.getMobile().fullPhoneNumber()).isEqualTo(MOBILE.fullPhoneNumber());
        assertThat(member.getAddress().fullAddress()).isEqualTo(ADDRESS.fullAddress());

        //verify
        verify(memberRepository).findById(any(Long.class));
        verify(memberRepository, times(1)).findById(any(Long.class));
    }

    @Test
    @DisplayName(value = "멤버 삭제")
    void deleteMemberTest() {
        //given
        Member member = getMember(NAME, LOGIN_ID, PASSWORD, EMAIL, BIRTH_DATE, AUTHOR_CODE, GENDER_CODE, MOBILE, ADDRESS);
        given(memberRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(member));

        //when
        memberService.deleteMember(any(Long.class));

        //then
        assertThatThrownBy(() -> memberService.findMember(member.getId()))
                .isInstanceOf(EntityNotFound.class)
                .hasMessageStartingWith(HAS_MESSAGE_STARTING_WITH)
                .hasMessageEndingWith("id = " + member.getId());

        //verify
        verify(memberRepository).findById(any(Long.class));
        verify(memberRepository, times(1)).findById(any(Long.class));
        verify(memberRepository).delete(any(Member.class));
        verify(memberRepository, times(1)).delete(any(Member.class));
    }
}