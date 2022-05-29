package com.heech.hellcoding.core.member.repository;

import com.heech.hellcoding.core.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryQuerydsl {

    Optional<Member> findByLoginId(String loginId);

    List<Member> findByName(String name);

}
