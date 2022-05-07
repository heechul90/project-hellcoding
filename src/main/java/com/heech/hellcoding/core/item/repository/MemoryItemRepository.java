package com.heech.hellcoding.core.item.repository;

import com.heech.hellcoding.core.item.domain.Item;
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
        item.setId(++sequence);
        store.put(item.getId(), item);
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
                .filter(item -> item.getItemName().equals(name))
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
        findItem.setItemName(item.getItemName());
        findItem.setPrice(item.getPrice());
        findItem.setQuantity(item.getQuantity());
        findItem.setOpen(item.getOpen());
        findItem.setRegionList(item.getRegionList());
        findItem.setItemType(item.getItemType());
        findItem.setDeliveryCode(item.getDeliveryCode());
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
