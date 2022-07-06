package com.heech.hellcoding.core.shop.delivery.repository;

import com.heech.hellcoding.core.shop.delivery.domain.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

}
