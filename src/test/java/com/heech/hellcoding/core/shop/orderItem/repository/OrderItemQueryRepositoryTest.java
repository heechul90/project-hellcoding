package com.heech.hellcoding.core.shop.orderItem.repository;

import com.heech.hellcoding.core.category.domain.Category;
import com.heech.hellcoding.core.category.domain.ServiceName;
import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.shop.ShopTestConfig;
import com.heech.hellcoding.core.shop.delivery.domain.Delivery;
import com.heech.hellcoding.core.shop.item.book.domain.Book;
import com.heech.hellcoding.core.shop.order.domain.Order;
import com.heech.hellcoding.core.shop.orderItem.domain.OrderItem;
import com.heech.hellcoding.core.shop.orderItem.dto.OrderItemSearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(ShopTestConfig.class)
class OrderItemQueryRepositoryTest {

    @PersistenceContext EntityManager em;

    @Autowired OrderItemQueryRepository orderItemQueryRepository;

    private Member addMember(String memberName, String loginId, String password, String email, Address address) {
        Member member = Member.createMemberBuilder()
                .name(memberName)
                .loginId(loginId)
                .password(password)
                .email(email)
                .address(address)
                .build();
        return member;
    }

    private Book addItem(String itemName, int price, int stockQuantity, Category category, String author) {
        Book book = Book.createBookBuilder()
                .name(itemName)
                .price(price)
                .stockQuantity(stockQuantity)
                .category(category)
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

    private Category getCategory() {
        Category category = Category.createCategoryBuilder()
                .parent(null)
                .serviceName(ServiceName.SHOP)
                .serialNumber(1)
                .name("category_name")
                .content("category_name")
                .build();
        em.persist(category);
        return category;
    }

    @Test
    void findOrderItems() {
        //given
        Address address = new Address("11111", "서울", "강남대로");
        Member member = addMember("tester", "tester", "1234", "tester@spring.com", address);
        Category category = getCategory();
        Book book1 = addItem("book1", 10000, 150, category, "author1");
        Book book2 = addItem("book2", 15000, 150, category, "author2");

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
        Page<OrderItem> content = orderItemQueryRepository.findOrderItems(condition, pageRequest);

        //then
        assertThat(content.getTotalElements()).isEqualTo(2);
        assertThat(content.getContent().size()).isEqualTo(2);
        assertThat(content.getContent()).extracting("orderPrice").containsExactly(10000, 15000);
        assertThat(content.getContent().get(0).getItem().getName()).isEqualTo("book1");
        assertThat(content.getContent().get(1).getItem().getName()).isEqualTo("book2");
    }
}