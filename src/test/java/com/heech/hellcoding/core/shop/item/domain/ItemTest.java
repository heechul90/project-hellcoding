package com.heech.hellcoding.core.shop.item.domain;

import com.heech.hellcoding.core.common.exception.NotEnoghStockException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class ItemTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void createItemTest() throws Exception{
        //given
        Book book = Book.builder()
                .author("테스트")
                .isbn(UUID.randomUUID().toString().toUpperCase())
                .build();
        book.createItem("테스트", "테스트", "테스트", 10000, 10);

        //when
        em.persist(book);
        em.flush();
        em.clear();

        //then
        Book findBook = em.find(Book.class, book.getId());
        assertThat(findBook.getAuthor()).isEqualTo("테스트");

    }

    @Test
    @Rollback(value = false)
    public void addStockTest() throws Exception{
        //given
        Book book = Book.builder()
                .author("테스트")
                .isbn(UUID.randomUUID().toString().toUpperCase())
                .build();
        book.createItem("테스트", "테스트", "테스트", 10000, 10);
        em.persist(book);
        em.flush();
        em.clear();

        //when
        Book findBook = em.find(Book.class, book.getId());
        findBook.addStock(100);

        //then
        assertThat(findBook.getStockQuantity()).isEqualTo(110);
    }

    @Test
    @Rollback(value = false)
    public void removeStockTest() throws Exception{
        //given
        Book book = Book.builder()
                .author("테스트")
                .isbn(UUID.randomUUID().toString().toUpperCase())
                .build();
        book.createItem("테스트", "테스트", "테스트", 10000, 10);
        em.persist(book);
        em.flush();
        em.clear();

        //when
        Book findBook = em.find(Book.class, book.getId());
        findBook.removeStock(5);

        //then
        assertThat(findBook.getStockQuantity()).isEqualTo(5);
        assertThatThrownBy(() -> findBook.removeStock(50))
                .isInstanceOf(NotEnoghStockException.class);
    }

}
