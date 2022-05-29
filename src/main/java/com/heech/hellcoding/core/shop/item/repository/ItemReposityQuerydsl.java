package com.heech.hellcoding.core.shop.item.repository;

import com.heech.hellcoding.core.shop.item.domain.Item;
import com.heech.hellcoding.core.shop.item.dto.ItemSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemReposityQuerydsl {

    Page<Item> findItems(ItemSearchCondition condition, Pageable pageable);
}
