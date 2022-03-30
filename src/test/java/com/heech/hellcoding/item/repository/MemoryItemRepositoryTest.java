package com.heech.hellcoding.item.repository;

import com.heech.hellcoding.item.domain.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemoryItemRepositoryTest {

    MemoryItemRepository itemRepository = new MemoryItemRepository();

    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Item item = new Item("itemA", 10000, 10);

        //when
        Item saveItem = itemRepository.save(item);

        //then
        Item findItem = itemRepository.findById(item.getId()).get();
        assertThat(findItem).isEqualTo(saveItem);
    }

    @Test
    void findById() {
        //given
        Item item = new Item("itemA", 10000, 10);
        Item saveItem = itemRepository.save(item);

        //when
        Item findItem = itemRepository.findById(saveItem.getId()).get();

        //then
        assertThat(findItem).isEqualTo(saveItem);
    }

    @Test
    void findByName() {
        //given
        Item item = new Item("itemA", 10000, 10);
        Item saveItem = itemRepository.save(item);

        //when
        Item findItem = itemRepository.findByName(item.getItemName()).get();

        //then
        assertThat(findItem).isEqualTo(saveItem);
    }

    @Test
    void update() {
        //given
        Item item = new Item("itemA", 10000, 10);

        Item saveItem = itemRepository.save(item);
        Long itemId = saveItem.getId();

        //when
        Item updateItem = new Item("itemB", 20000, 20);
        itemRepository.update(itemId, updateItem);

        Item findItem = itemRepository.findById(itemId).get();
        //then
        assertThat(findItem.getItemName()).isEqualTo(updateItem.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateItem.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateItem.getQuantity());
    }

    @Test
    void findAll() {
        //given
        Item itemA = new Item("itemA", 10000, 10);
        Item itemB = new Item("itemB", 10000, 10);

        itemRepository.save(itemA);
        itemRepository.save(itemB);

        //when
        List<Item> resultList = itemRepository.findAll();

        //then
        assertThat(resultList.size()).isEqualTo(2);
    }
}