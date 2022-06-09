package com.heech.hellcoding.core.shop.item.info.repository;

import com.heech.hellcoding.core.shop.item.book.domain.Book;
import com.heech.hellcoding.core.shop.item.info.domain.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    ItemRepository itemRepository;

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
    void findByIdIn() {
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