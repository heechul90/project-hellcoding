package com.heech.hellcoding.core.shop.delivery.repository;

import com.heech.hellcoding.core.shop.delivery.domain.Delivery;
import com.heech.hellcoding.core.shop.delivery.dto.DeliverySearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeliveryRepositoryQuerydsl {

    Page<Delivery> findDeliveries(DeliverySearchCondition condition, Pageable pageable);

}
