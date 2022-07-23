package com.heech.hellcoding.core.member.domain;

import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.member.dto.UpdateMemberParam;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberTest {

    @PersistenceContext
    EntityManager em;

    @Test
    void createMemberTest() {
        //given
        Member member1 = Member.createMemberBuilder()
                .loginId("spring1")
                .password("1234")
                .name("스프링")
                .email("spring@spring@.com")
                .build();

        Member member2 = Member.createMemberBuilder()
                .loginId("spring2")
                .password("1234")
                .name("스프링2")
                .email("spring2@spring.com")
                .birthDate("19901009")
                .genderCode(GenderCode.M)
                .mobile(new Mobile("010", "4250", "4296"))
                .address(new Address("12345", "Sejong", "hanuridaero"))
                .build();

        //when
        em.persist(member1);
        em.persist(member2);

        //then
        Member findMember1 = em.find(Member.class, member1.getId());
        Member findMember2 = em.find(Member.class, member2.getId());
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);
        assertThat(findMember2.getMobile()).isEqualTo(member2.getMobile());
        assertThat(findMember2.getAddress()).isEqualTo(member2.getAddress());
    }

    @Test
    public void updateMemberTest() {
        //given
        Member member = Member.createMemberBuilder()
                .loginId("spring1")
                .password("1234")
                .name("스프링1")
                .email("spring2@spring.com")
                .birthDate("19901009")
                .genderCode(GenderCode.M)
                .mobile(new Mobile("010", "4250", "4296"))
                .address(new Address("12345", "Sejong", "hanuridaero"))
                .build();
        em.persist(member);
        em.flush();
        em.clear();

        String updateParmaName = "changeName";
        String updateParamEmail = "";
        UpdateMemberParam param = UpdateMemberParam.builder()
                .name(updateParmaName)
                .email(updateParamEmail)
                .build();

        //when
        Member findMember = em.find(Member.class, member.getId());
        findMember.updateMember(param);
        em.flush();
        em.clear();

        //then
        Member updateMember = em.find(Member.class, member.getId());
        assertThat(updateMember.getName()).isEqualTo("changeName");
        assertThat(updateMember.getPassword()).isEqualTo("1234");
        assertThat(updateMember.getEmail()).isEqualTo(findMember.getEmail());
    }

    @Test
    void changePasswordTest() {
        //given
        Member member = Member.createMemberBuilder()
                .loginId("spring1")
                .password("1234")
                .name("스프링1")
                .email("spring2@spring.com")
                .birthDate("19901009")
                .genderCode(GenderCode.M)
                .mobile(new Mobile("010", "4250", "4296"))
                .address(new Address("12345", "Sejong", "hanuridaero"))
                .build();
        em.persist(member);
        em.flush();
        em.clear();

        //when
        Member findMember = em.find(Member.class, member.getId());
        findMember.changePassword("1111");
        em.flush();
        em.clear();

        //then
        Member updatedMember = em.find(Member.class, member.getId());
        assertThat(updatedMember.getPassword()).isEqualTo("1111");
    }

}