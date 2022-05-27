package com.heech.hellcoding.core.member.service;

import com.heech.hellcoding.core.member.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @PersistenceContext
    EntityManager em;

    @Test
    void findMembers() {
        //given
        Member member = new Member("test", "test", "test");
        memberService.saveMember(member);

        //when
        List<Member> members = memberService.findMembers();

        //then
        assertThat(members).extracting("name").contains("test");
    }

    @Test
    void saveMember() {
        //given
        Member member = new Member("test", "test", "test");

        //when
        Member savedMember = memberService.saveMember(member);

        //then
        assertThat(savedMember).isEqualTo(member);
        assertThat(savedMember.getLoginId()).isEqualTo("test");
        assertThat(savedMember.getName()).isEqualTo("test");
        assertThat(savedMember.getPassword()).isEqualTo("test");
    }

    @Test
    void updateMmeber() {
        //given
        Member member = new Member("test", "test", "test");
        Member savedMember = memberService.saveMember(member);
        em.flush();
        em.clear();

        //when
        memberService.updateMmeber(savedMember.getId(), "1111");

        //then
        Member findMember = memberService.findById(savedMember.getId()).orElse(null);
        assertThat(findMember.getPassword()).isEqualTo("1111");
    }

    @Test
    void deleteMember() {
        //given
        Member member = new Member("test", "test", "test");
        Member savedMember = memberService.saveMember(member);
        em.flush();
        em.clear();

        //when
        memberService.deleteMember(savedMember.getId());

        //then
        Member findMember = memberService.findById(savedMember.getId()).orElse(null);
        assertThat(findMember).isNull();
    }

    @Test
    void findById() {
        //given
        Member member = new Member("test", "test", "test");
        Member savedMember = memberService.saveMember(member);
        em.flush();
        em.clear();

        //when
        Member findMember = memberService.findById(savedMember.getId()).orElse(null);

        //then
        assertThat(findMember.getLoginId()).isEqualTo("test");
        assertThat(findMember.getName()).isEqualTo("test");
        assertThat(findMember.getPassword()).isEqualTo("test");
    }
}