package com.heech.hellcoding.core.member.service;

import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.member.MemberTestConfig;
import com.heech.hellcoding.core.member.domain.GenderCode;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.domain.Mobile;
import com.heech.hellcoding.core.member.dto.MemberSearchCondition;
import com.heech.hellcoding.core.member.dto.UpdateMemberParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({MemberTestConfig.class})
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @PersistenceContext
    EntityManager em;

    @Test
    void findMembersTest() {
        //given
        Member member = Member.createMemberBuilder()
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
        assertThat(content.getTotalElements()).isEqualTo(1);
        assertThat(content.getContent()).extracting("name").contains("test");
    }

    @Test
    void saveMemberTest() {
        //given
        Member member = Member.createMemberBuilder()
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
    void updateMmeberTest() {
        //given
        Member member = Member.createMemberBuilder()
                .name("name")
                .loginId("loginId")
                .password("test")
                .email("email@mail.com")
                .build();
        Member savedMember = memberService.saveMember(member);

        //when
        UpdateMemberParam param = UpdateMemberParam.builder()
                .name("update_name")
                .email("update_email@mail.com")
                .birthDate("19901009")
                .genderCode(GenderCode.M)
                .mobile(new Mobile("010", "4250", "4296"))
                .address(new Address("11112", "세종시", "601호"))
                .build();
        memberService.updateMember(savedMember.getId(), param);

        //then
        Member findMember = em.find(Member.class, savedMember.getId());
        assertThat(findMember.getName()).isEqualTo("update_name");
        assertThat(findMember.getLoginId()).isEqualTo("loginId");
        assertThat(findMember.getEmail()).isEqualTo("update_email@mail.com");
        assertThat(findMember.getBirthDate()).isEqualTo("19901009");
        assertThat(findMember.getGenderCode()).isEqualTo(GenderCode.M);
        assertThat(findMember.getMobile().fullPhoneNumber()).isEqualTo("010-4250-4296");
        assertThat(findMember.getAddress().fullAddress()).isEqualTo("(11112) 세종시 601호");
    }

    @Test
    void deleteMemberTest() {
        //given
        Member member = Member.createMemberBuilder()
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
    void findMemberTest() {
        //given
        Member member = Member.createMemberBuilder()
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