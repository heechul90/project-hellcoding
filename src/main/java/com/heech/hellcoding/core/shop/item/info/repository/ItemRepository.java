package com.heech.hellcoding.core.shop.item.info.repository;

import com.heech.hellcoding.core.shop.item.info.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByIdIn(List<Long> ids);
}
