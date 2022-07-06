package com.heech.hellcoding.core.member.repository;

import com.heech.hellcoding.core.common.dto.SearchCondition;
import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.member.domain.GenderCode;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.domain.Mobile;
import com.heech.hellcoding.core.member.dto.MemberSearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @PersistenceContext EntityManager em;

    @Autowired MemberRepository memberRepository;

    @Autowired MemberQueryRepository memberQueryRepository;

    private Member getMember(Mobile mobile, Address address) {
        Member member = Member.createMemberBuilder()
                .name("test_name")
                .loginId("test_loginId")
                .password("test_password")
                .email("test_email@email.com")
                .birthDate("19901009")
                .genderCode(GenderCode.M)
                .mobile(mobile)
                .address(address)
                .build();
        return member;
    }

    @Test
    void save() {
        //given
        Mobile mobile = new Mobile("010", "4250", "4296");
        Address address = new Address("30152", "세종시 한누리대로 2135", "스타힐타워A 601호");
        Member member = getMember(mobile, address);

        //when
        memberRepository.save(member);
        em.flush();
        em.clear();

        //then
        Member findMember = memberRepository.findById(member.getId()).orElse(null);
        assertThat(findMember.getName()).isEqualTo("test_name");
        assertThat(findMember.getLoginId()).isEqualTo("test_loginId");
        assertThat(findMember.getPassword()).isEqualTo("test_password");
        assertThat(findMember.getEmail()).isEqualTo("test_email@email.com");
        assertThat(findMember.getBirthDate()).isEqualTo("19901009");
        assertThat(findMember.getGenderCode()).isEqualTo(GenderCode.M);
        assertThat(findMember.getMobile().getMobileNumberFirst()).isEqualTo("010");
        assertThat(findMember.getMobile().getMobileNumberMiddle()).isEqualTo("4250");
        assertThat(findMember.getMobile().getMobileNumberLast()).isEqualTo("4296");
        assertThat(findMember.getAddress().getZipcode()).isEqualTo(address.getZipcode());
        assertThat(findMember.getAddress().getAddress()).isEqualTo(address.getAddress());
        assertThat(findMember.getAddress().getDetailAddress()).isEqualTo(address.getDetailAddress());
    }

    @Test
    void findById() {
        //given
        Mobile mobile = new Mobile("010", "4250", "4296");
        Address address = new Address("30152", "세종시 한누리대로 2135", "스타힐타워A 601호");
        Member member = getMember(mobile, address);
        memberRepository.save(member);

        //when
        Member findMember = memberRepository.findById(member.getId()).orElse(null);

        //then
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    void findByName() {
        //given
        Mobile mobile = new Mobile("010", "4250", "4296");
        Address address = new Address("30152", "세종시 한누리대로 2135", "스타힐타워A 601호");
        Member member = getMember(mobile, address);
        memberRepository.save(member);

        //when
        List<Member> resultList1 = memberRepository.findByName("test");
        List<Member> resultList2 = memberRepository.findByName("test_name");

        //then
        assertThat(resultList1.size()).isEqualTo(0);
        assertThat(resultList2.size()).isEqualTo(1);
        assertThat(resultList2).extracting("name").containsExactly("test_name");
    }

    @Test
    void update() {
        //given
        Mobile mobile = new Mobile("010", "4250", "4296");
        Address address = new Address("30152", "세종시 한누리대로 2135", "스타힐타워A 601호");
        Member member = getMember(mobile, address);
        memberRepository.save(member);

        //when
        Member findMember = memberRepository.findById(member.getId()).orElse(null);
        findMember.updateMemberBuilder()
                .name("update_name")
                .email("update_email")
                .build();
        em.flush();
        em.clear();

        //then
        Member updatedMember = memberRepository.findById(member.getId()).orElse(null);
        assertThat(updatedMember.getName()).isEqualTo("update_name");
        assertThat(updatedMember.getEmail()).isEqualTo("update_email");
    }

    @Test
    void delete() {
        //given
        Mobile mobile = new Mobile("010", "4250", "4296");
        Address address = new Address("30152", "세종시 한누리대로 2135", "스타힐타워A 601호");
        Member member = getMember(mobile, address);
        memberRepository.save(member);

        //when
        memberRepository.delete(member);

        //then
        Member findMember = memberRepository.findById(member.getId()).orElse(null);
        assertThat(findMember).isNull();
    }

    @Test
    void findByLoginId() {
        //given
        Mobile mobile = new Mobile("010", "4250", "4296");
        Address address = new Address("30152", "세종시 한누리대로 2135", "스타힐타워A 601호");
        Member member = getMember(mobile, address);
        memberRepository.save(member);

        //when
        Member findMember = memberRepository.findByLoginId("test_loginId").orElse(null);

        //then
        assertThat(findMember.getName()).isEqualTo("test_name");
    }

    @Test
    void findMembersTest() {
        //given
        Mobile mobile = new Mobile("010", "4250", "4296");
        Address address = new Address("30152", "세종시 한누리대로 2135", "스타힐타워A 601호");
        Member member = getMember(mobile, address);
        memberRepository.save(member);

        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setSearchCondition(SearchCondition.NAME);
        condition.setSearchKeyword("test");
        condition.setSearchGender(GenderCode.M);

        PageRequest pageRequest = PageRequest.of(0, 10);

        //when
        Page<Member> resultList = memberQueryRepository.findMembers(condition, pageRequest);

        //then
        assertThat(resultList).extracting("name").containsExactly("test_name");
    }
}