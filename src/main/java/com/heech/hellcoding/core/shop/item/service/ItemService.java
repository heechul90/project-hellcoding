package com.heech.hellcoding.core.shop.item.service;

import com.heech.hellcoding.core.shop.item.domain.Item;
import com.heech.hellcoding.core.shop.item.dto.ItemSearchCondition;
import com.heech.hellcoding.core.shop.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    /**
     * 상품 목록 조회
     */
    public Page<Item> findItems(ItemSearchCondition condition, Pageable pageable) {
        return itemRepository.findItems(condition, pageable);
    }

    /**
     * 상품 저장
     */
    @Transactional
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    /**
     * 상품 단건 조회
     */
    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id);
    }

    /**
     * 상품 이름 조회
     */
    public List<Item> findByName(String name) {
        return itemRepository.findByName(name);
    }


}
