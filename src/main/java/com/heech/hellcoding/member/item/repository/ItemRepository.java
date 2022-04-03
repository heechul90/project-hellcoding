package com.heech.hellcoding.member.item.repository;

import com.heech.hellcoding.member.item.domain.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {

    Item save(Item item);

    Optional<Item> findById(Long id);

    Optional<Item> findByName(String name);

    void update(Long id, Item item);

    List<Item> findAll();

}
