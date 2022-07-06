package com.heech.hellcoding.core.member.domain;

import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import com.heech.hellcoding.core.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

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
                .signupDate(LocalDateTime.now())
                .signinDate(LocalDateTime.now())
                .build();

        //when
        memberRepository.save(member1);
        memberRepository.save(member2);

        //then
        Member findMember1 = memberRepository.findById(member1.getId()).orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));
        Member findMember2 = memberRepository.findById(member2.getId()).orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);
        assertThat(findMember2.getMobile()).isEqualTo(member2.getMobile());
        assertThat(findMember2.getAddress()).isEqualTo(member2.getAddress());
    }

    @Test
    public void updateMemberTest() throws Exception{
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
                .signupDate(LocalDateTime.now())
                .signinDate(LocalDateTime.now())
                .build();
        Member savedMember = memberRepository.save(member);
        em.flush();
        em.clear();

        String updateParmaName = "changeName";
        String updateParamPassword = "1111";
        String updateParamEmail = "";

        //when
        Member findMember = memberRepository.findById(member.getId()).orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));
        findMember.updateMember(updateParmaName, updateParamPassword, updateParamEmail);
        em.flush();
        em.clear();

        //then
        Member updateMember = memberRepository.findById(member.getId()).orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));
        assertThat(updateMember.getName()).isEqualTo("changeName");
        assertThat(updateMember.getPassword()).isEqualTo("1111");
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
                .signupDate(LocalDateTime.now())
                .signinDate(LocalDateTime.now())
                .build();
        Member savedMember = memberRepository.save(member);

        //when
        savedMember.changePassword("1111");
        em.flush();
        em.clear();

        //then
        Member findMember = memberRepository.findById(member.getId()).orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));
        assertThat(findMember.getPassword()).isEqualTo("1111");
    }

}