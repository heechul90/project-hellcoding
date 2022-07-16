package com.heech.hellcoding.core.shop;

import com.heech.hellcoding.core.member.repository.MemberRepository;
import com.heech.hellcoding.core.shop.delivery.repository.DeliveryQueryRepository;
import com.heech.hellcoding.core.shop.delivery.repository.DeliveryRepository;
import com.heech.hellcoding.core.shop.delivery.service.DeliveryService;
import com.heech.hellcoding.core.shop.item.info.domain.Item;
import com.heech.hellcoding.core.shop.item.info.repository.ItemRepository;
import com.heech.hellcoding.core.shop.order.repository.OrderQueryRepository;
import com.heech.hellcoding.core.shop.order.repository.OrderRepository;
import com.heech.hellcoding.core.shop.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@TestConfiguration
public class ShopTestConfig {

    @PersistenceContext EntityManager em;

    @Autowired DeliveryRepository deliveryRepository;

    @Autowired OrderRepository orderRepository;

    @Autowired MemberRepository memberRepository;

    @Autowired ItemRepository itemRepository;

    @Bean
    public DeliveryQueryRepository deliveryQueryRepository() {
        return new DeliveryQueryRepository(em);
    }

    @Bean
    public DeliveryService deliveryService() {
        return new DeliveryService(deliveryRepository, deliveryQueryRepository());
    }

    @Bean
    public OrderQueryRepository orderQueryRepository() {
        return new OrderQueryRepository(em);
    }

    @Bean
    public OrderService orderService() {
        return new OrderService(orderRepository, orderQueryRepository(), memberRepository, itemRepository);
    }

}
