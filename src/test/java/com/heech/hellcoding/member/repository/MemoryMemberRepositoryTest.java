package com.heech.hellcoding.member.repository;

import com.heech.hellcoding.member.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    void save() {
        //given
        Member member = new Member();
        member.setLoginId("springId");
        member.setName("springName");
        member.setPassword("springPassword");

        //when
        Member savedMember = repository.save(member);

        //then
        Optional<Member> findMember = repository.findByLoginId(member.getLoginId());
        assertThat(findMember.get().getLoginId()).isEqualTo(member.getLoginId());
    }

    @Test
    void findById() {
        //given
        Member member = new Member();
        member.setLoginId("springId");
        member.setName("springName");
        member.setPassword("springPassword");
        Member savedMember = repository.save(member);

        //when
        Member findMember = repository.findById(savedMember.getId());

        //then
        assertThat(findMember).isEqualTo(savedMember);
    }

    @Test
    void findByLoginId() {
        //given
        Member member = new Member();
        member.setLoginId("springId");
        member.setName("springName");
        member.setPassword("springPassword");
        Member savedMember = repository.save(member);

        //when
        Optional<Member> findMember = repository.findByLoginId(savedMember.getLoginId());

        //then
        assertThat(findMember.get()).isEqualTo(savedMember);
    }

    @Test
    void findByName() {
        //given
        Member member = new Member();
        member.setLoginId("springId");
        member.setName("springName");
        member.setPassword("springPassword");
        Member savedMember = repository.save(member);

        //when
        Optional<Member> findMember = repository.findByName(member.getName());

        //then
        assertThat(findMember.get()).isEqualTo(savedMember);
    }

    @Test
    void findAll() {
        //given
        Member memberA = new Member();
        memberA.setLoginId("memberA");
        memberA.setName("memberA");
        memberA.setPassword("memberA");

        Member memberB = new Member();
        memberB.setLoginId("memberB");
        memberB.setName("memberB");
        memberB.setPassword("memberB");

        repository.save(memberA);
        repository.save(memberB);

        //when
        List<Member> resultList = repository.findAll();

        //then
        assertThat(resultList.size()).isEqualTo(2);
    }
}