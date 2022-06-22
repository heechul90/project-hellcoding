package com.heech.hellcoding.core.shop.orderItem.service;

import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.shop.delivery.domain.Delivery;
import com.heech.hellcoding.core.shop.delivery.domain.DeliveryStatus;
import com.heech.hellcoding.core.shop.item.book.domain.Book;
import com.heech.hellcoding.core.shop.order.domain.Order;
import com.heech.hellcoding.core.shop.orderItem.domain.OrderItem;
import com.heech.hellcoding.core.shop.orderItem.dto.OrderItemSearchCondition;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderItemServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    OrderItemService orderItemService;

    private Member addMember(String memberName, String loginId, String password, String email, Address address) {
        Member member = Member.builder()
                .name(memberName)
                .loginId(loginId)
                .password(password)
                .email(email)
                .address(address)
                .build();
        return member;
    }

    private Book addItem(String itemName, int price, int stockQuantity, String author) {
        Book book = Book.createBookBuilder()
                .name(itemName)
                .price(price)
                .stockQuantity(stockQuantity)
                .author(author)
                .build();
        return book;
    }

    private OrderItem getOrderItem(Book book, int count) {
        return OrderItem.createOrderItemBuilder()
                .item(book)
                .orderPrice(book.getPrice())
                .count(count)
                .build();
    }

    @Test
    void findOrderItemsTest() {
        //given
        Address address = new Address("11111", "서울", "강남대로");
        Member member = addMember("tester", "tester", "1234", "tester@spring.com", address);
        Book book1 = addItem("book1", 10000, 150, "author1");
        Book book2 = addItem("book2", 15000, 150, "author2");

        em.persist(member);
        em.persist(book1);
        em.persist(book2);

        Delivery delivery = new Delivery(member.getAddress());
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem1 = getOrderItem(book1, 10);
        OrderItem orderItem2 = getOrderItem(book2, 10);
        orderItems.add(orderItem1);
        orderItems.add(orderItem2);

        Order order = new Order(member, delivery, orderItems);
        em.persist(order);
        em.flush();
        em.clear();

        //when
        OrderItemSearchCondition condition = new OrderItemSearchCondition();
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<OrderItem> content = orderItemService.findOrderItems(condition, pageRequest);

        //then
        assertThat(content.getContent().size()).isEqualTo(2);
        assertThat(content.getTotalElements()).isEqualTo(2);
        assertThat(content.getContent()).extracting("count").containsExactly(10, 10);
    }

    @Test
    void findOrderItemTest() {
        //given
        Address address = new Address("11111", "서울", "강남대로");
        Member member = addMember("tester", "tester", "1234", "tester@spring.com", address);
        Book book1 = addItem("book1", 10000, 150, "author1");
        Book book2 = addItem("book2", 15000, 150, "author2");

        em.persist(member);
        em.persist(book1);
        em.persist(book2);

        Delivery delivery = new Delivery(member.getAddress());
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem1 = getOrderItem(book1, 10);
        OrderItem orderItem2 = getOrderItem(book2, 10);
        orderItems.add(orderItem1);
        orderItems.add(orderItem2);

        Order order = new Order(member, delivery, orderItems);
        em.persist(order);
        em.flush();
        em.clear();

        //when
        OrderItem findOrderItem = orderItemService.findOrderItem(orderItem1.getId());

        //then
        assertThat(findOrderItem.getCount()).isEqualTo(10);
        assertThat(findOrderItem.getItem().getName()).isEqualTo("book1");
    }

    @Test
    void addOrderItemTest() {
        //given
        Address address = new Address("11111", "서울", "강남대로");
        Member member = addMember("tester", "tester", "1234", "tester@spring.com", address);
        Book book1 = addItem("book1", 10000, 150, "author1");
        Book book2 = addItem("book2", 15000, 150, "author2");

        em.persist(member);
        em.persist(book1);
        em.persist(book2);

        Delivery delivery = new Delivery(member.getAddress());
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem1 = getOrderItem(book1, 10);
        OrderItem orderItem2 = getOrderItem(book2, 10);
        orderItems.add(orderItem1);
        orderItems.add(orderItem2);

        Order order = new Order(member, delivery, orderItems);
        em.persist(order);
        em.flush();
        em.clear();

        //when
        Long addedOrderItem = orderItemService.addOrderItem(order.getId(), book1.getId(), 15);

        //then
        OrderItemSearchCondition condition = new OrderItemSearchCondition();
        condition.setSearchOrderId(order.getId());
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<OrderItem> content = orderItemService.findOrderItems(condition, pageRequest);

        assertThat(content.getContent().size()).isEqualTo(3);
        assertThat(content.getTotalElements()).isEqualTo(3);
        assertThat(content.getContent()).extracting("count").containsExactly(10, 10, 15);

        OrderItem findOrderItem = orderItemService.findOrderItem(addedOrderItem);
        assertThat(findOrderItem.getOrderPrice()).isEqualTo(10000);
        assertThat(findOrderItem.getCount()).isEqualTo(15);
    }

    @Test
    void updateOrderItemTest() {
        //given
        Address address = new Address("11111", "서울", "강남대로");
        Member member = addMember("tester", "tester", "1234", "tester@spring.com", address);
        Book book1 = addItem("book1", 10000, 150, "author1");
        Book book2 = addItem("book2", 15000, 150, "author2");

        em.persist(member);
        em.persist(book1);
        em.persist(book2);

        Delivery delivery = new Delivery(member.getAddress());
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem1 = getOrderItem(book1, 10);
        OrderItem orderItem2 = getOrderItem(book2, 10);
        orderItems.add(orderItem1);
        orderItems.add(orderItem2);

        Order order = new Order(member, delivery, orderItems);
        em.persist(order);
        em.flush();
        em.clear();

        //when
        orderItemService.updateOrderItem(orderItem1.getId(), 30000, 50);
        em.flush();
        em.clear();

        //then
        OrderItem findOrderItem = orderItemService.findOrderItem(orderItem1.getId());
        assertThat(findOrderItem.getOrderPrice()).isEqualTo(30000);
        assertThat(findOrderItem.getCount()).isEqualTo(50);
        assertThat(findOrderItem.getItem().getStockQuantity()).isEqualTo(100);
    }

    @Test
    void deleteOrderItemTest() {
        //given
        Address address = new Address("11111", "서울", "강남대로");
        Member member = addMember("tester", "tester", "1234", "tester@spring.com", address);
        Book book1 = addItem("book1", 10000, 150, "author1");
        Book book2 = addItem("book2", 15000, 150, "author2");

        em.persist(member);
        em.persist(book1);
        em.persist(book2);

        Delivery delivery = new Delivery(member.getAddress());
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem1 = getOrderItem(book1, 10);
        OrderItem orderItem2 = getOrderItem(book2, 10);
        orderItems.add(orderItem1);
        orderItems.add(orderItem2);

        Order order = new Order(member, delivery, orderItems);
        em.persist(order);
        em.flush();
        em.clear();

        //when
        orderItemService.deleteOrderItem(orderItem1.getId());

        //then
        assertThatThrownBy(() -> orderItemService.findOrderItem(orderItem1.getId()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("조회에");
    }
}