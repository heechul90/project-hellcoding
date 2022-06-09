package com.heech.hellcoding.core.shop.order.service;

import com.heech.hellcoding.api.shop.order.request.ItemInfo;
import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.shop.delivery.domain.DeliveryStatus;
import com.heech.hellcoding.core.shop.item.book.domain.Book;
import com.heech.hellcoding.core.shop.item.info.repository.ItemRepository;
import com.heech.hellcoding.core.shop.order.domain.Order;
import com.heech.hellcoding.core.shop.order.domain.OrderStatus;
import com.heech.hellcoding.core.shop.order.dto.OrderSearchCondition;
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

@SpringBootTest
@Transactional
class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    ItemRepository itemRepository;

    private List<ItemInfo> getItemInfos(Long itemId1, Long itemId2, int orderCount) {
        List<ItemInfo> itemInfos = new ArrayList<>();
        ItemInfo itemInfo1 = new ItemInfo();
        itemInfo1.setItemId(itemId1);
        itemInfo1.setOrderCount(orderCount);
        itemInfos.add(itemInfo1);

        ItemInfo itemInfo2 = new ItemInfo();
        itemInfo2.setItemId(itemId2);
        itemInfo2.setOrderCount(orderCount);
        itemInfos.add(itemInfo2);
        return itemInfos;
    }

    @Test
    void findOrdersTest() {
        //when
        Address address = new Address("11111", "서울", "강남대로");
        Member member1 = addMember("tester1", "tester1", "12341", "tester1@spring.com", address);
        Member member2 = addMember("tester2", "tester2", "12342", "tester2@spring.com", address);
        Book book1 = addItem("book1", 10000, 5000, "author1");
        Book book2 = addItem("book2", 15000, 5000, "author2");
        Book book3 = addItem("book3", 20000, 5000, "author3");
        Book book4 = addItem("book4", 25000, 5000, "author4");
        Book book5 = addItem("book5", 30000, 5000, "author5");

        em.persist(member1);
        em.persist(member2);
        em.persist(book1);
        em.persist(book2);
        em.persist(book3);
        em.persist(book4);
        em.persist(book5);

        List<ItemInfo> itemInfos1 = getItemInfos(book1.getId(), book2.getId(), 10);
        List<ItemInfo> itemInfos2 = getItemInfos(book2.getId(), book3.getId(), 20);
        List<ItemInfo> itemInfos3 = getItemInfos(book3.getId(), book4.getId(), 30);
        List<ItemInfo> itemInfos4 = getItemInfos(book4.getId(), book5.getId(), 40);
        List<ItemInfo> itemInfos5 = getItemInfos(book5.getId(), book1.getId(), 50);

        for (int i = 0; i < 50; i++) {
            if (i < 10) {
                orderService.saveOrder(member1.getId(), itemInfos1);
            } else if (i < 20) {
                orderService.saveOrder(member2.getId(), itemInfos2);
            } else if (i < 30) {
                orderService.saveOrder(member1.getId(), itemInfos3);
            } else if (i < 40) {
                orderService.saveOrder(member2.getId(), itemInfos4);
            } else {
                orderService.saveOrder(member1.getId(), itemInfos5);
            }
        }
        em.flush();
        em.clear();

        //when
        OrderSearchCondition condition = new OrderSearchCondition();
        //condition.setSearchOrderStatus(OrderStatus.CANCEL);
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Order> content = orderService.findOrders(condition, pageRequest);

        //then
        assertThat(content.getContent().size()).isEqualTo(10);
        assertThat(content.getContent().get(0).getOrderItems().get(0).getItem().getName()).isEqualTo("book1");
        assertThat(content.getTotalElements()).isEqualTo(50);
    }

    @Test
    void findOrderTest() {
        //given
        Address address = new Address("11111", "서울", "강남대로");
        Member member = addMember("tester", "tester", "1234", "tester@spring.com", address);
        Book book1 = addItem("book1", 10000, 150, "author1");
        Book book2 = addItem("book2", 15000, 150, "author2");

        em.persist(member);
        em.persist(book1);
        em.persist(book2);

        List<ItemInfo> itemInfos = getItemInfos(book1.getId(), book2.getId(), 10);
        Long savedId = orderService.saveOrder(member.getId(), itemInfos);
        em.flush();
        em.clear();

        //when
        Order findOrder = orderService.findOrder(savedId);

        //then
        assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(findOrder.getMember().getName()).isEqualTo("tester");
        assertThat(findOrder.getDelivery().getAddress().getDetailAddress()).isEqualTo("강남대로");
        assertThat(findOrder.getOrderItems().get(0).getItem().getName()).isEqualTo("book1");
    }

    @Test
    void saveOrderTest() {
        //given
        Address address = new Address("11111", "서울", "강남대로");
        Member member = addMember("tester", "tester", "1234", "tester@spring.com", address);
        Book book1 = addItem("book1", 10000, 150, "author1");
        Book book2 = addItem("book2", 15000, 150, "author2");

        em.persist(member);
        em.persist(book1);
        em.persist(book2);
        em.flush();
        em.clear();

        List<ItemInfo> itemInfos = getItemInfos(book1.getId(), book2.getId(), 10);

        //when
        Long savedId = orderService.saveOrder(member.getId(), itemInfos);

        //then
        Order findOrder = orderService.findOrder(savedId);
        assertThat(findOrder.getId()).isEqualTo(savedId);
        assertThat(findOrder.getDelivery().getStatus()).isEqualTo(DeliveryStatus.READY);
        assertThat(findOrder.getMember().getName()).isEqualTo("tester");
        assertThat(findOrder.getOrderItems().size()).isEqualTo(1);
    }

    @Test
    void updateOrder() {
    }

    @Test
    void deleteOrder() {
    }

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
}