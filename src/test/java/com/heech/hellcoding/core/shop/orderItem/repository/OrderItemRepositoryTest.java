package com.heech.hellcoding.core.shop.orderItem.repository;

import com.heech.hellcoding.core.category.domain.Category;
import com.heech.hellcoding.core.category.domain.ServiceName;
import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.shop.item.book.domain.Book;
import com.heech.hellcoding.core.shop.orderItem.domain.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderItemRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    OrderItemRepository orderItemRepository;

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
    public void saveTest() {
        //given
        Address address = new Address("11111", "서울", "강남대로");
        Member member = addMember("tester", "tester", "1234", "tester@spring.com", address);
        Category category = getCategory();
        Book book1 = addItem("book1", 10000, 150, category, "author1");
        Book book2 = addItem("book2", 15000, 150, category, "author2");

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
    public void updateTest() {
        //given
        Address address = new Address("11111", "서울", "강남대로");
        Member member = addMember("tester", "tester", "1234", "tester@spring.com", address);
        Category category = getCategory();
        Book book1 = addItem("book1", 10000, 150, category, "author1");
        Book book2 = addItem("book2", 15000, 150, category, "author2");

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