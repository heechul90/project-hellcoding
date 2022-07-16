package com.heech.hellcoding.core.member.repository;

import com.heech.hellcoding.core.common.dto.SearchCondition;
import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.member.MemberTestConfig;
import com.heech.hellcoding.core.member.domain.GenderCode;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.domain.Mobile;
import com.heech.hellcoding.core.member.dto.MemberSearchCondition;
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
@Import(MemberTestConfig.class)
class MemberQueryRepositoryTest {

    @PersistenceContext EntityManager em;

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
    void findMembersTest() {
        //given
        Mobile mobile = new Mobile("010", "4250", "4296");
        Address address = new Address("30152", "세종시 한누리대로 2135", "스타힐타워A 601호");
        Member member = getMember(mobile, address);
        em.persist(member);

        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setSearchCondition(SearchCondition.NAME);
        condition.setSearchKeyword("test");
        condition.setSearchGender(GenderCode.M);

        PageRequest pageRequest = PageRequest.of(0, 10);

        //when
        Page<Member> content = memberQueryRepository.findMembers(condition, pageRequest);

        //then
        assertThat(content.getTotalElements()).isEqualTo(1);
        assertThat(content).extracting("name").containsExactly("test_name");
    }
}