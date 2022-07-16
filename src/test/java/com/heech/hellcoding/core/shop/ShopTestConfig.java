package com.heech.hellcoding.core.shop;

import com.heech.hellcoding.core.shop.delivery.repository.DeliveryQueryRepository;
import com.heech.hellcoding.core.shop.delivery.repository.DeliveryRepository;
import com.heech.hellcoding.core.shop.delivery.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@TestConfiguration
public class ShopTestConfig {

    @PersistenceContext EntityManager em;

    @Autowired DeliveryRepository deliveryRepository;

    @Bean
    public DeliveryQueryRepository deliveryQueryRepository() {
        return new DeliveryQueryRepository(em);
    }

    @Bean
    public DeliveryService deliveryService() {
        return new DeliveryService(deliveryRepository, deliveryQueryRepository());
    }

}
