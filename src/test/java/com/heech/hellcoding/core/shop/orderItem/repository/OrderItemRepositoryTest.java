package com.heech.hellcoding.core.shop.orderItem.repository;

import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.shop.delivery.domain.Delivery;
import com.heech.hellcoding.core.shop.delivery.domain.DeliveryStatus;
import com.heech.hellcoding.core.shop.item.book.domain.Book;
import com.heech.hellcoding.core.shop.order.domain.Order;
import com.heech.hellcoding.core.shop.orderItem.domain.OrderItem;
import com.heech.hellcoding.core.shop.orderItem.dto.OrderItemSearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderItemRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    OrderItemRepository orderItemRepository;

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
        Book book = Book.createBuilder()
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
    public void findOrderItemsTest() throws Exception{
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
        condition.setSearchOrderId(order.getId());
        condition.setSearchOrderPriceGoe(10000);
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<OrderItem> content = orderItemRepository.findOrderItems(condition, pageRequest);

        //then
        assertThat(content.getTotalElements()).isEqualTo(2);
        assertThat(content.getContent().size()).isEqualTo(2);
        assertThat(content.getContent()).extracting("orderPrice").containsExactly(10000, 15000);
        assertThat(content.getContent().get(0).getItem().getName()).isEqualTo("book1");
        assertThat(content.getContent().get(1).getItem().getName()).isEqualTo("book2");
    }

    @Test
    public void saveTest() throws Exception{
        //given
        Address address = new Address("11111", "서울", "강남대로");
        Member member = addMember("tester", "tester", "1234", "tester@spring.com", address);
        Book book1 = addItem("book1", 10000, 150, "author1");
        Book book2 = addItem("book2", 15000, 150, "author2");

        em.persist(member);
        em.persist(book1);
        em.persist(book2);

        OrderItem orderItem = new OrderItem(book1, book1.getPrice(), 10);

        //when
        OrderItem savedOrderItem = orderItemRepository.save(orderItem);

        //then
        OrderItem findOrderItem = orderItemRepository.findById(savedOrderItem.getId()).orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));
        assertThat(findOrderItem.getCount()).isEqualTo(10);
        assertThat(findOrderItem.getOrderPrice()).isEqualTo(10000);
    }

    @Test
    public void updateTest() throws Exception{
        //given
        Address address = new Address("11111", "서울", "강남대로");
        Member member = addMember("tester", "tester", "1234", "tester@spring.com", address);
        Book book1 = addItem("book1", 10000, 150, "author1");
        Book book2 = addItem("book2", 15000, 150, "author2");

        em.persist(member);
        em.persist(book1);
        em.persist(book2);

        OrderItem orderItem = new OrderItem(book1, book1.getPrice(), 10);
        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
        em.flush();
        em.clear();

        //when
        OrderItem findOrderItem = orderItemRepository.findById(savedOrderItem.getId()).orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));
        findOrderItem.updateOrderitemBuilder()
                .count(20)
                .build();
        em.flush();
        em.clear();

        //then
        OrderItem updatedOrderItem = orderItemRepository.findById(savedOrderItem.getId()).orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));
        assertThat(updatedOrderItem.getCount()).isEqualTo(20);
        assertThat(updatedOrderItem.getItem().getStockQuantity()).isEqualTo(130);
    }

}