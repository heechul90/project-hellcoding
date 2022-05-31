package com.heech.hellcoding.core.member.service;

import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.dto.MemberSearchCondition;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        Member member = Member.builder()
                .name("test")
                .loginId("test")
                .password("test")
                .email("test")
                .build();
        memberService.saveMember(member);

        MemberSearchCondition condition = new MemberSearchCondition();
        PageRequest pageRequest = PageRequest.of(0, 10);

        //when
        Page<Member> content = memberService.findMembers(condition, pageRequest);

        //then
        assertThat(content.getContent()).extracting("name").contains("test");
    }

    @Test
    void saveMember() {
        //given
        Member member = Member.builder()
                .name("test")
                .loginId("test")
                .password("test")
                .email("test")
                .build();

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
        Member member = Member.builder()
                .name("test")
                .loginId("test")
                .password("test")
                .email("test")
                .build();
        Member savedMember = memberService.saveMember(member);
        em.flush();
        em.clear();

        //when
        memberService.updateMember(savedMember.getId(), "", "1111", "");

        //then
        Member findMember = em.find(Member.class, savedMember.getId());
        assertThat(findMember.getPassword()).isEqualTo("1111");
    }

    @Test
    void deleteMember() {
        //given
        Member member = Member.builder()
                .name("test")
                .loginId("test")
                .password("test")
                .email("test")
                .build();
        Member savedMember = memberService.saveMember(member);
        em.flush();
        em.clear();

        //when
        memberService.deleteMember(savedMember.getId());

        //then
        Member findMember = em.find(Member.class, savedMember.getId());
        assertThat(findMember).isNull();
    }

    @Test
    void findById() {
        //given
        Member member = Member.builder()
                .name("test")
                .loginId("test")
                .password("test")
                .email("test")
                .build();
        Member savedMember = memberService.saveMember(member);
        em.flush();
        em.clear();

        //when
        Member findMember = memberService.findMember(savedMember.getId());

        //then
        assertThat(findMember.getLoginId()).isEqualTo("test");
        assertThat(findMember.getName()).isEqualTo("test");
        assertThat(findMember.getPassword()).isEqualTo("test");
    }
}