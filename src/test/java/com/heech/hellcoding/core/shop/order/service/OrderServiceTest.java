package com.heech.hellcoding.core.shop.order.service;

import com.heech.hellcoding.api.shop.order.request.ItemInfo;
import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.shop.delivery.domain.DeliveryStatus;
import com.heech.hellcoding.core.shop.item.book.domain.Book;
import com.heech.hellcoding.core.shop.item.info.domain.Item;
import com.heech.hellcoding.core.shop.item.info.dto.ItemSearchCondition;
import com.heech.hellcoding.core.shop.item.info.repository.ItemRepository;
import com.heech.hellcoding.core.shop.order.domain.Order;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Test
    @Rollback(value = false)
    void findOrders() {
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
        em.flush();
        em.clear();

        //when
        for (int i = 0; i < 50; i++) {
            if (i < 10) {
                orderService.saveOrder(member1.getId(), book1.getId(), i);
            } else if (i < 20) {
                orderService.saveOrder(member2.getId(), book2.getId(), i);
            } else if (i < 30) {
                orderService.saveOrder(member1.getId(), book3.getId(), i);
            } else if (i < 40) {
                orderService.saveOrder(member2.getId(), book4.getId(), i);
            } else {
                orderService.saveOrder(member1.getId(), book5.getId(), i);
            }
        }

        ItemSearchCondition condition = new ItemSearchCondition();
        condition.getSearchIds().add(book1.getId());
        condition.getSearchIds().add(book2.getId());

        List<Item> items = itemRepository.findByIdIn(condition.getSearchIds());


    }

    @Test
    void findOrder() {
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

        //when
        Long savedId = orderService.saveOrder(member.getId(), book1.getId(), 10);

        //then
        Order findOrder = orderService.findOrder(savedId);
        assertThat(findOrder.getId()).isEqualTo(savedId);
        assertThat(findOrder.getDelivery().getStatus()).isEqualTo(DeliveryStatus.READY);
        assertThat(findOrder.getMember().getName()).isEqualTo("tester");
        assertThat(findOrder.getOrderItems().size()).isEqualTo(1);
    }

    @Test
    @Rollback(value = false)
    public void saveOrderTestTest() throws Exception{
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

        //when
        SaveItemDto saveItemDto = new SaveItemDto();
        saveItemDto.setMemberId(member.getId());

        ItemInfo itemInfo1 = new ItemInfo();
        itemInfo1.setItemId(book1.getId());
        itemInfo1.setOrderCount(10);

        ItemInfo itemInfo2 = new ItemInfo();
        itemInfo2.setItemId(book2.getId());
        itemInfo2.setOrderCount(15);

        saveItemDto.getItemInfos().add(itemInfo1);
        saveItemDto.getItemInfos().add(itemInfo2);

        Long savedId = orderService.saveOrderTest(member.getId(), saveItemDto.getItemInfos());

        //then
    }

    @Data
    static class SaveItemDto {
        private Long memberId;
        private List<ItemInfo> itemInfos = new ArrayList<>();
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