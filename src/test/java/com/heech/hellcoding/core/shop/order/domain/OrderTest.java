package com.heech.hellcoding.core.shop.order.domain;

import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.shop.delivery.domain.Delivery;
import com.heech.hellcoding.core.shop.delivery.domain.DeliveryStatus;
import com.heech.hellcoding.core.shop.item.book.domain.Book;
import com.heech.hellcoding.core.shop.orderItem.domain.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class OrderTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void createOrderTest() throws Exception{
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

        //조회
        Member findMember = em.find(Member.class, member.getId());
        Book findBook1 = em.find(Book.class, book1.getId());
        Book findBook2 = em.find(Book.class, book2.getId());

        Delivery delivery = new Delivery(findMember.getAddress(), DeliveryStatus.READY);
        OrderItem orderItem1 = OrderItem.createOrderItem()
                .item(findBook1)
                .orderPrice(findBook1.getPrice())
                .count(10)
                .build();
        OrderItem orderItem2 = OrderItem.createOrderItem()
                .item(findBook2)
                .orderPrice(findBook2.getPrice())
                .count(10)
                .build();

        Order order = new Order(findMember, delivery, orderItem1, orderItem2);

        //when
        em.persist(order);
        em.flush();
        em.clear();

        //then
        Order findOrder = em.find(Order.class, order.getId());
        Book checkBook1 = em.find(Book.class, book1.getId());
        Book checkBook2 = em.find(Book.class, book2.getId());
        assertThat(findOrder.getOrderItems()).extracting("count").containsExactly(10, 10);
        assertThat(findOrder.getOrderItems().size()).isEqualTo(2);
        assertThat(checkBook1.getStockQuantity()).isEqualTo(140);
        assertThat(checkBook2.getStockQuantity()).isEqualTo(140);
    }

    @Test
    public void cancelTest() throws Exception{
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

        Member findMember = em.find(Member.class, member.getId());
        Book findBook1 = em.find(Book.class, book1.getId());
        Book findBook2 = em.find(Book.class, book2.getId());

        Delivery delivery = new Delivery(findMember.getAddress(), DeliveryStatus.READY);
        OrderItem orderItem1 = OrderItem.createOrderItem()
                .item(findBook1)
                .orderPrice(findBook1.getPrice())
                .count(10)
                .build();
        OrderItem orderItem2 = OrderItem.createOrderItem()
                .item(findBook2)
                .orderPrice(findBook2.getPrice())
                .count(10)
                .build();

        Order order = new Order(findMember, delivery, orderItem1, orderItem2);

        em.persist(order);
        em.flush();
        em.clear();

        //when
        Order findOrder = em.find(Order.class, order.getId());
        findOrder.cancel();

        //then
        Book checkBook1 = em.find(Book.class, book1.getId());
        Book checkBook2 = em.find(Book.class, book2.getId());
        assertThat(checkBook1.getStockQuantity()).isEqualTo(150);
        assertThat(checkBook2.getStockQuantity()).isEqualTo(150);
    }

    @Test
    public void getTotalPriceTest() throws Exception{
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

        Member findMember = em.find(Member.class, member.getId());
        Book findBook1 = em.find(Book.class, book1.getId());
        Book findBook2 = em.find(Book.class, book2.getId());

        Delivery delivery = new Delivery(findMember.getAddress(), DeliveryStatus.READY);
        OrderItem orderItem1 = OrderItem.createOrderItem()
                .item(findBook1)
                .orderPrice(findBook1.getPrice())
                .count(10)
                .build();
        OrderItem orderItem2 = OrderItem.createOrderItem()
                .item(findBook2)
                .orderPrice(findBook2.getPrice())
                .count(10)
                .build();

        Order order = new Order(findMember, delivery, orderItem1, orderItem2);

        em.persist(order);
        em.flush();
        em.clear();

        //when
        Order findOrder = em.find(Order.class, order.getId());

        //then
        assertThat(findOrder.getTotalPrice())
                .isEqualTo(orderItem1.getOrderPrice() * orderItem1.getCount() + orderItem2.getOrderPrice() * orderItem2.getCount());
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
