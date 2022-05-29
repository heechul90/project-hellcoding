package com.heech.hellcoding.core.shop.order.domain;

import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.repository.MemberRepository;
import com.heech.hellcoding.core.shop.order.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
public class OrderTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void createOrderTest() throws Exception{
        //given
        Member member = memberRepository.findByLoginId("spring").orElse(null);

        //OrderItem orderItem1 = OrderItem.createOrderItem(new Book("홍길동", "11111"));
        //Order.createOrder(member, new Delivery(member.getAddress(), DeliveryStatus.READY), new OrderItem())


        //when

        //then
    }

}
