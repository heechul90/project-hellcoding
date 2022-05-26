package com.heech.hellcoding.core.shop.order.repository;

import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.shop.delivery.domain.Delivery;
import com.heech.hellcoding.core.shop.delivery.domain.DeliveryStatus;
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
    void saveOrderTest() throws Exception {
        //given
        Member member = new Member("loginId", "memberA", "1111");
        Order order = new Order(LocalDateTime.now(), OrderStatus.ORDER, member, new Delivery());

        //when
        Order savedOrder = orderRepository.save(order);

        //then
        Order findOrder = orderRepository.findById(order.getId()).orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));
        assertThat(findOrder).isEqualTo(order);
        assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
    }

    @Test
    void findByIdTest() {
        //given
        Member member = new Member("loginId", "memberA", "1111");
        Address address = new Address("12345", "Seoul", "gangnamdaero");
        Delivery delivery = new Delivery(address, DeliveryStatus.READY);
        Order order = new Order(LocalDateTime.now(), OrderStatus.ORDER, member, delivery);
        Order savedOrder = orderRepository.save(order);

        //when
        Order findOrder = orderRepository.findById(order.getId()).orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));

        //then
        assertThat(findOrder).isEqualTo(order);
        assertThat(findOrder.getMember()).isEqualTo(member);
        assertThat(findOrder.getDelivery().getAddress()).isEqualTo(address);
        assertThat(findOrder.getDelivery().getStatus()).isEqualTo(DeliveryStatus.READY);
    }

}