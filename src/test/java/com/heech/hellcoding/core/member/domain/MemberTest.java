package com.heech.hellcoding.core.member.domain;

import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.member.dto.UpdateMemberParam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberTest {

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
    public static final String UPDATE_PASSWORD = "update_" + PASSWORD;

    @PersistenceContext
    EntityManager em;

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
    @DisplayName(value = "member entity 생성 후 저장 확인")
    void createMemberTest() {
        //given
        Member member = getMember(NAME, LOGIN_ID, PASSWORD, EMAIL, BIRTH_DATE, AUTHOR_CODE, GENDER_CODE, MOBILE, ADDRESS);

        //when
        em.persist(member);

        //then
        Member findMember = em.find(Member.class, member.getId());
        assertThat(findMember.getName()).isEqualTo(NAME);
        assertThat(findMember.getLoginId()).isEqualTo(LOGIN_ID);
        assertThat(findMember.getPassword()).isEqualTo(PASSWORD);
        assertThat(findMember.getEmail()).isEqualTo(EMAIL);
        assertThat(findMember.getBirthDate()).isEqualTo(BIRTH_DATE);
        assertThat(findMember.getAuthorCode()).isEqualTo(AUTHOR_CODE);
        assertThat(findMember.getGenderCode()).isEqualTo(GENDER_CODE);
        assertThat(findMember.getMobile().fullPhoneNumber()).isEqualTo(MOBILE.fullPhoneNumber());
        assertThat(findMember.getAddress().fullAddress()).isEqualTo(ADDRESS.fullAddress());
    }

    @Test
    @DisplayName(value = "member entity 생성 후 수정 확인")
    public void updateMemberTest() {
        //given
        Member member = getMember(NAME, LOGIN_ID, PASSWORD, EMAIL, BIRTH_DATE, AUTHOR_CODE, GENDER_CODE, MOBILE, ADDRESS);
        em.persist(member);

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
        Member findMember = em.find(Member.class, member.getId());
        findMember.updateMember(param);

        //then
        Member updatedMember = em.find(Member.class, member.getId());
        assertThat(updatedMember.getName()).isEqualTo(UPDATE_NAME);
        assertThat(updatedMember.getEmail()).isEqualTo(UPDATE_EMAIL);
        assertThat(updatedMember.getBirthDate()).isEqualTo(BIRTH_DATE);
        assertThat(updatedMember.getAuthorCode()).isEqualTo(AUTHOR_CODE);
        assertThat(updatedMember.getGenderCode()).isEqualTo(GENDER_CODE);
        assertThat(updatedMember.getMobile().fullPhoneNumber()).isEqualTo(MOBILE.fullPhoneNumber());
        assertThat(updatedMember.getAddress().fullAddress()).isEqualTo(ADDRESS.fullAddress());
    }

    @Test
    @DisplayName(value = "member entity 생성 후 비밀번호 수정 확인")
    void changePasswordTest() {
        //given
        Member member = getMember(NAME, LOGIN_ID, PASSWORD, EMAIL, BIRTH_DATE, AUTHOR_CODE, GENDER_CODE, MOBILE, ADDRESS);
        em.persist(member);

        //when
        Member findMember = em.find(Member.class, member.getId());
        findMember.changePassword(UPDATE_PASSWORD);

        //then
        Member updatedMember = em.find(Member.class, member.getId());
        assertThat(updatedMember.getPassword()).isEqualTo(UPDATE_PASSWORD);
    }

}