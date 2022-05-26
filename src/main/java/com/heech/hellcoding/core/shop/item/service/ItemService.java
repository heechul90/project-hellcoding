package com.heech.hellcoding.core.shop.item.service;

import com.heech.hellcoding.core.shop.item.domain.Item;
import com.heech.hellcoding.core.shop.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    /**
     * 상품 저장
     * @param item
     * @return
     */
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    /**
     * 상품찾기(아이디)
     * @param id
     * @return
     */
    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id);
    }

    /**
     * 상품찾기(네임)
     * @param name
     * @return
     */
    public List<Item> findByName(String name) {
        return itemRepository.findByName(name);
    }

    /**
     * 상품 목록
     * @return
     */
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

}
