package com.heech.hellcoding.core.shop.order.repository;

import com.heech.hellcoding.core.category.domain.Category;
import com.heech.hellcoding.core.category.domain.ServiceName;
import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.shop.ShopTestConfig;
import com.heech.hellcoding.core.shop.delivery.domain.Delivery;
import com.heech.hellcoding.core.shop.delivery.domain.DeliveryStatus;
import com.heech.hellcoding.core.shop.item.book.domain.Book;
import com.heech.hellcoding.core.shop.order.domain.Order;
import com.heech.hellcoding.core.shop.order.dto.OrderSearchCondition;
import com.heech.hellcoding.core.shop.orderItem.domain.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(ShopTestConfig.class)
class OrderQueryRepositoryTest {

    @PersistenceContext EntityManager em;

    @Autowired OrderQueryRepository orderQueryRepository;

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

    @Test
    void findOrdersTest() {
        Address address = new Address("11111", "서울", "강남대로");
        Member member = addMember("tester", "tester", "1234", "tester@spring.com", address);
        Category category = getCategory();

        Book book1 = addItem("book1", 10000, 150, category, "author1");
        Book book2 = addItem("book2", 15000, 150, category, "author2");

        em.persist(member);
        em.persist(category);
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
        Page<Order> content = orderQueryRepository.findOrders(condition, pageRequest);

        //then
        assertThat(content.getContent().size()).isEqualTo(1);
        assertThat(content.getContent().get(0).getOrderItems().size()).isEqualTo(2);
        assertThat(content.getContent().get(0).getMember().getName()).isEqualTo("tester");
        assertThat(content.getContent().get(0).getDelivery().getStatus()).isEqualTo(DeliveryStatus.READY);
    }
}