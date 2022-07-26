package com.heech.hellcoding.core.member.domain;

import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.member.dto.UpdateMemberParam;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberTempTest {

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

    @Mock EntityManager em;

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
    public void createMemberTest() throws Exception{
        //given
        Member member = getMember(NAME, LOGIN_ID, PASSWORD, EMAIL, BIRTH_DATE, AUTHOR_CODE, GENDER_CODE, MOBILE, ADDRESS);
        given(em.find(Member.class, any(Long.class))).willReturn(member);

        //when
        em.persist(Member.class);

        //then
    }

}