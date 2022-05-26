package com.heech.hellcoding.core.shop.order.repository;

import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.shop.delivery.domain.Delivery;
import com.heech.hellcoding.core.shop.order.domain.Order;
import com.heech.hellcoding.core.shop.order.domain.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    void createOrder() {
        //given
        Member member = new Member("loginId", "memberA", "1111");
        Order order = new Order(LocalDateTime.now(), OrderStatus.ORDER, member, new Delivery());

        //when
        Order savedOrder = orderRepository.save(order);

        //then
        Order findOrder = orderRepository.findById(order.getId()).orElse(null);
        assertThat(findOrder).isEqualTo(order);
        assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
    }

}