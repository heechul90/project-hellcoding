package com.heech.hellcoding.core.shop.item.repository;

import com.heech.hellcoding.core.shop.item.domain.Item;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryItemRepository implements ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>();
    private static long sequence = 0L;

    /**
     * Item 저장
     * @param item
     * @return
     */
    @Override
    public Item save(Item item) {
        store.put(++sequence, item);
        return item;
    }

    /**
     * Item 상세(Id)
     * @param id
     * @return
     */
    @Override
    public Optional<Item> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    /**
     * Item 상세(Name)
     * @param name
     * @return
     */
    @Override
    public Optional<Item> findByName(String name) {
        return store.values().stream()
                .filter(item -> item.getName().equals(name))
                .findAny();
    }

    /**
     * 상품 수정
     * @param id
     * @param item
     */
    @Override
    public void update(Long id, Item item) {
        Item findItem = findById(id).get();
        findItem.changeItem(findItem.getName(), findItem.getPrice(), findItem.getQuantity());
    }

    /**
     * 상품 목록
     * @return
     */
    @Override
    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
