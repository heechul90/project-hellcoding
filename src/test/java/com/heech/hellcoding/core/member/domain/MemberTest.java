package com.heech.hellcoding.core.member.domain;

import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContexts;
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
        Member member1 = new Member("spring1", "스프링1", "1234");
        Mobile mobile = new Mobile("010", "4250", "4296");
        Address address = new Address("12345", "Sejong", "hanuridaero");
        Member member2 = new Member("spring2", "1234", "스프링2", "spring2@spring.com", "19901009", GenderCode.M, mobile, address,
                LocalDateTime.now(), LocalDateTime.now());

        //when
        memberRepository.save(member1);
        memberRepository.save(member2);

        //then
        Member findMember1 = memberRepository.findById(member1.getId()).orElseGet(null);
        Member findMember2 = memberRepository.findById(member2.getId()).orElseGet(null);
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);
        assertThat(findMember2.getMobile()).isEqualTo(mobile);
        assertThat(findMember2.getAddress()).isEqualTo(address);
    }

    @Test
    void changePasswordTest() {
        //given
        Member member = new Member("spring1", "스프링1", "1234");
        Member savedMember = memberRepository.save(member);

        //when
        savedMember.changePassword("1111");
        em.flush();
        em.clear();

        //then
        Member findMember = memberRepository.findById(member.getId()).orElseGet(null);
        assertThat(findMember.getPassword()).isEqualTo("1111");
    }

}