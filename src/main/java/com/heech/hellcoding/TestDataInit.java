package com.heech.hellcoding;

import com.heech.hellcoding.item.domain.Item;
import com.heech.hellcoding.item.repository.ItemRepository;
import com.heech.hellcoding.member.domain.Member;
import com.heech.hellcoding.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));

        memberRepository.save(new Member("spring", "스프링", "1234"));
    }

}
