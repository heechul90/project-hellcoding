package com.heech.hellcoding.core.shop.order.repository;

import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.repository.MemberRepository;
import com.heech.hellcoding.core.shop.delivery.domain.Delivery;
import com.heech.hellcoding.core.shop.delivery.domain.DeliveryStatus;
import com.heech.hellcoding.core.shop.item.book.domain.Book;
import com.heech.hellcoding.core.shop.item.repository.ItemRepository;
import com.heech.hellcoding.core.shop.order.domain.Order;
import com.heech.hellcoding.core.shop.orderItem.domain.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ItemRepository itemRepository;

    @Test
    @Rollback(value = false)
    void saveOrderTest() throws Exception {
        //given
        Member member = memberRepository.findByLoginId("spring").orElse(null);
        Book book = (Book) itemRepository.findByName("연금술사").get(0);
        Delivery delivery = new Delivery(member.getAddress(), DeliveryStatus.READY);
        em.persist(delivery);

        OrderItem orderItem = OrderItem.createOrderItem(book, book.getPrice(), 2);
        Order order = Order.createOrder(member, delivery, orderItem);

        //when
        Order savedOrder = orderRepository.save(order);
        em.flush();
        em.clear();

        //then
        Order findOrder = orderRepository.findById(savedOrder.getId()).orElse(null);
        //Order findOrder = orderRepository.findById(order.getId()).orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));
        assertThat(findOrder.getDelivery().getStatus()).isEqualTo(DeliveryStatus.READY);
    }

}