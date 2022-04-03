package com.heech.hellcoding.item.repository;

import com.heech.hellcoding.item.domain.Item;
import com.heech.hellcoding.item.repository.ItemRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryItemRepository implements ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    @Override
    public Optional<Item> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Item> findByName(String name) {
        return store.values().stream()
                .filter(item -> item.getItemName().equals(name))
                .findAny();
    }

    @Override
    public void update(Long id, Item item) {
        Item findItem = findById(id).get();
        findItem.setItemName(item.getItemName());
        findItem.setPrice(item.getPrice());
        findItem.setQuantity(item.getQuantity());
    }

    @Override
    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
