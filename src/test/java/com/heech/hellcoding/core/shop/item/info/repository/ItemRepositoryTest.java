package com.heech.hellcoding.core.shop.item.info.repository;

import com.heech.hellcoding.core.category.domain.Category;
import com.heech.hellcoding.core.category.domain.ServiceName;
import com.heech.hellcoding.core.shop.item.book.domain.Book;
import com.heech.hellcoding.core.shop.item.info.domain.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ItemRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    ItemRepository itemRepository;

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

    private Book addItem(String itemName, int price, int stockQuantity, String author) {
        Book book = Book.createBookBuilder()
                .name(itemName)
                .price(price)
                .stockQuantity(stockQuantity)
                .category(getCategory())
                .author(author)
                .build();
        return book;
    }

    @Test
    void findByIdInTest() {
        //given
        Book book1 = addItem("book1", 10000, 150, "author1");
        Book book2 = addItem("book2", 15000, 150, "author2");
        em.persist(book1);
        em.persist(book2);

        //when
        List<Long> ids = new ArrayList<>();
        ids.add(book1.getId());
        ids.add(book2.getId());
        List<Item> items = itemRepository.findByIdIn(ids);

        //then
        assertThat(items.size()).isEqualTo(2);
        assertThat(items).extracting("name").containsExactly("book1", "book2");
    }
}