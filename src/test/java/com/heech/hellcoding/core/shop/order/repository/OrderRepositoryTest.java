package com.heech.hellcoding.core.shop.order.repository;

import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.repository.MemberRepository;
import com.heech.hellcoding.core.shop.delivery.domain.Delivery;
import com.heech.hellcoding.core.shop.delivery.domain.DeliveryStatus;
import com.heech.hellcoding.core.shop.item.book.domain.Book;
import com.heech.hellcoding.core.shop.order.domain.Order;
import com.heech.hellcoding.core.shop.order.dto.OrderSearchCondition;
import com.heech.hellcoding.core.shop.orderItem.domain.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Test
    @Rollback(value = false)
    void findOrdersTest() throws Exception {
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

        Delivery delivery = new Delivery(findMember.getAddress());
        OrderItem orderItem1 = OrderItem.createOrderItemBuilder()
                .item(findBook1)
                .orderPrice(findBook1.getPrice())
                .count(10)
                .build();
        OrderItem orderItem2 = OrderItem.createOrderItemBuilder()
                .item(findBook2)
                .orderPrice(findBook2.getPrice())
                .count(10)
                .build();

        Order order = new Order(findMember, delivery, orderItem1, orderItem2);

        em.persist(order);
        em.flush();
        em.clear();

        //when
        OrderSearchCondition condition = new OrderSearchCondition();

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Order> content = orderRepository.findOrders(condition, pageRequest);

        //then
        assertThat(content.getContent().size()).isEqualTo(1);
        assertThat(content.getContent().get(0).getOrderItems().size()).isEqualTo(2);
        assertThat(content.getContent().get(0).getMember().getName()).isEqualTo("tester");
    }

}