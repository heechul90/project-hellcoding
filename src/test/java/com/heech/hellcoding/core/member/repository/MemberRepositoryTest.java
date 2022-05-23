package com.heech.hellcoding.core.member.repository;

import com.heech.hellcoding.core.member.domain.Address;
import com.heech.hellcoding.core.member.domain.GenderCode;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.domain.Mobile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
//@Rollback(value = false)
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void save() {
        //given
        Mobile mobile = new Mobile("010", "4250", "4296");
        Address address = new Address("30152", "세종시 한누리대로 2135", "스타힐타워A 601gh");

        Member member1 = new Member("loginId1", "1234", "member1", "coding1234@coding.com", "19900101",
                GenderCode.M, mobile, address, LocalDateTime.now(), LocalDateTime.now());
        Member member2 = new Member("loginId2", "1234", "member2", "coding1234@coding.com", "19900101",
                GenderCode.M, mobile, address, LocalDateTime.now(), LocalDateTime.now());

        //when
        memberRepository.save(member1);
        memberRepository.save(member2);

        //then
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);
        assertThat(findMember1.getName()).isEqualTo("member1");
        assertThat(findMember2.getName()).isEqualTo("member2");
        assertThat(findMember1.getMobile().getMobileNumberFirst()).isEqualTo("010");
        assertThat(findMember1.getMobile().getMobileNumberMiddle()).isEqualTo("4250");
        assertThat(findMember1.getMobile().getMobileNumberLast()).isEqualTo("4296");
    }

    @Test
    void findById() {
        //given
        Mobile mobile = new Mobile("010", "4250", "4296");
        Address address = new Address("30152", "세종시 한누리대로 2135", "스타힐타워A 601gh");

        Member member1 = new Member("loginId1", "1234", "member1", "coding1234@coding.com", "19900101",
                GenderCode.M, mobile, address, LocalDateTime.now(), LocalDateTime.now());
        Member member2 = new Member("loginId2", "1234", "member2", "coding1234@coding.com", "19900101",
                GenderCode.M, mobile, address, LocalDateTime.now(), LocalDateTime.now());

        memberRepository.save(member1);
        memberRepository.save(member2);

        //when
        Member findMember = memberRepository.findById(member1.getId()).get();

        //then
        assertThat(findMember).isEqualTo(member1);
    }

    @Test
    void findAll() {
        //given
        Mobile mobile = new Mobile("010", "4250", "4296");
        Address address = new Address("30152", "세종시 한누리대로 2135", "스타힐타워A 601gh");

        Member member1 = new Member("loginId1", "1234", "member1", "coding1234@coding.com", "19900101",
                GenderCode.M, mobile, address, LocalDateTime.now(), LocalDateTime.now());
        Member member2 = new Member("loginId2", "1234", "member2", "coding1234@coding.com", "19900101",
                GenderCode.M, mobile, address, LocalDateTime.now(), LocalDateTime.now());

        memberRepository.save(member1);
        memberRepository.save(member2);

        //when
        List<Member> resultList = memberRepository.findAll();

        //then
        assertThat(resultList.size()).isEqualTo(2);
    }

    @Test
    void findByName() {
        //given
        Mobile mobile = new Mobile("010", "4250", "4296");
        Address address = new Address("30152", "세종시 한누리대로 2135", "스타힐타워A 601gh");

        Member member1 = new Member("loginId1", "1234", "member1", "coding1234@coding.com", "19900101",
                GenderCode.M, mobile, address, LocalDateTime.now(), LocalDateTime.now());
        Member member2 = new Member("loginId2", "1234", "member2", "coding1234@coding.com", "19900101",
                GenderCode.M, mobile, address, LocalDateTime.now(), LocalDateTime.now());

        memberRepository.save(member1);
        memberRepository.save(member2);

        //when
        List<Member> resultList = memberRepository.findByName("member1");

        //then
        assertThat(resultList.size()).isEqualTo(1);
        assertThat(resultList).extracting("name").containsExactly("member1");
    }

    @Test
    void update() {
        //given
        Mobile mobile = new Mobile("010", "4250", "4296");
        Address address = new Address("30152", "세종시 한누리대로 2135", "스타힐타워A 601gh");

        Member member1 = new Member("loginId1", "1234", "member1", "coding1234@coding.com", "19900101",
                GenderCode.M, mobile, address, LocalDateTime.now(), LocalDateTime.now());
        Member member2 = new Member("loginId2", "1234", "member2", "coding1234@coding.com", "19900101",
                GenderCode.M, mobile, address, LocalDateTime.now(), LocalDateTime.now());

        memberRepository.save(member1);
        memberRepository.save(member2);

        //when
        member1.changePassword("4321");

        //then
        Member findMember = memberRepository.findById(member1.getId()).get();
        assertThat(findMember.getPassword()).isEqualTo("4321");
    }

    @Test
    void delete() {
        //given
        Mobile mobile = new Mobile("010", "4250", "4296");
        Address address = new Address("30152", "세종시 한누리대로 2135", "스타힐타워A 601gh");

        Member member1 = new Member("loginId1", "1234", "member1", "coding1234@coding.com", "19900101",
                GenderCode.M, mobile, address, LocalDateTime.now(), LocalDateTime.now());
        Member member2 = new Member("loginId2", "1234", "member2", "coding1234@coding.com", "19900101",
                GenderCode.M, mobile, address, LocalDateTime.now(), LocalDateTime.now());

        memberRepository.save(member1);
        memberRepository.save(member2);

        //when
        memberRepository.delete(member1);

        //then
        List<Member> resultList = memberRepository.findAll();
        assertThat(resultList.size()).isEqualTo(1);
    }

    @Test
    void findByLoginId() {
        //given
        Mobile mobile = new Mobile("010", "4250", "4296");
        Address address = new Address("30152", "세종시 한누리대로 2135", "스타힐타워A 601gh");

        Member member1 = new Member("loginId1", "1234", "member1", "coding1234@coding.com", "19900101",
                GenderCode.M, mobile, address, LocalDateTime.now(), LocalDateTime.now());
        Member member2 = new Member("loginId2", "1234", "member2", "coding1234@coding.com", "19900101",
                GenderCode.M, mobile, address, LocalDateTime.now(), LocalDateTime.now());

        memberRepository.save(member1);
        memberRepository.save(member2);

        //when
        Member findMember = memberRepository.findByLoginId("loginId2").get();

        //then
        assertThat(findMember.getName()).isEqualTo("member2");
    }

}