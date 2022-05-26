package com.heech.hellcoding;

import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.member.domain.GenderCode;
import com.heech.hellcoding.core.member.domain.Mobile;
import com.heech.hellcoding.core.shop.item.domain.Book;
import com.heech.hellcoding.core.shop.item.domain.Item;
import com.heech.hellcoding.core.shop.item.repository.ItemRepository;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    @PostConstruct
    public void init() {
        /*
        itemRepository.save(new Book("이희철하나", UUID.randomUUID().toString().toUpperCase()));
        itemRepository.save(new Book("이희철둘", UUID.randomUUID().toString().toUpperCase()));
        */

        Mobile mobile = new Mobile("010", "4250", "4296");
        Address address = new Address("12345", "Sejong", "hanuridaero");

        Member admin = new Member(
                "admin",
                "1234",
                "관리자",
                "spring2@spring.com",
                "19901009",
                GenderCode.M,
                mobile,
                address,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        memberRepository.save(admin);


        Member spring = new Member(
                "spring",
                "1234",
                "스프링",
                "spring2@spring.com",
                "19901009",
                GenderCode.M,
                mobile,
                address,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        memberRepository.save(spring);
    }

}
