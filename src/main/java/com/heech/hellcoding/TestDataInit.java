package com.heech.hellcoding;

import com.heech.hellcoding.core.shop.item.domain.Book;
import com.heech.hellcoding.core.shop.item.domain.Item;
import com.heech.hellcoding.core.shop.item.repository.ItemRepository;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    @PostConstruct
    public void init() {
        itemRepository.save(new Book("이희철하나", UUID.randomUUID().toString().toUpperCase()));
        itemRepository.save(new Book("이희철둘", UUID.randomUUID().toString().toUpperCase()));

        Member member = new Member(
                "admin",
                "관리자",
                "1234"
        );
        memberRepository.save(member);
    }

}
