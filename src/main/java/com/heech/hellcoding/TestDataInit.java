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
        Book book1 = Book.builder()
                .author("파울로 코엘료")
                .isbn(UUID.randomUUID().toString().toUpperCase())
                .build();

        book1.createItem("연금술사", "2022년 베스트 셀러!", "재미있는 책입니다", 10800, 100);
        itemRepository.save(book1);

        Book book2 = new Book("리처드 바크", UUID.randomUUID().toString().toUpperCase());
        book2.createItem("갈매기의 꿈", "2021년 베스트 셀러!", "200만이 선택한 책입니다.", 7200, 200);
        itemRepository.save(book2);

        Mobile mobile = new Mobile("010", "4250", "4296");
        Address address = new Address("12345", "Sejong", "hanuridaero");

        Member admin = new Member(
                "admin",
                "1234",
                "스프링관리자",
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
                "스프링유저",
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
