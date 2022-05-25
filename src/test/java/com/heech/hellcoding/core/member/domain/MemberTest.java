package com.heech.hellcoding.core.member.domain;

import com.heech.hellcoding.core.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void createMember() {
        //given


        //when

        //then
    }

}