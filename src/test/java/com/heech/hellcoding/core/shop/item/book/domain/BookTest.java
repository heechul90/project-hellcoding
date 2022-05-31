package com.heech.hellcoding.core.shop.item.book.domain;

import com.heech.hellcoding.core.common.exception.NotEnoghStockException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class BookTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void createBookTest() throws Exception{
        //given
        Book book = Book.createBuilder()
                .name("test")
                .author("test")
                .build();

        //when
        em.persist(book);
        em.flush();
        em.clear();

        //then
        Book findBook = em.find(Book.class, book.getId());
        assertThat(findBook.getName()).isEqualTo("test");
        assertThat(findBook.getAuthor()).isEqualTo("test");
        assertThat(findBook.getPrice()).isZero();
    }

    @Test
    public void updateBookTest() throws Exception{
        //given
        Book book = Book.createBuilder()
                .name("test")
                .author("test")
                .build();
        em.persist(book);
        em.flush();
        em.clear();

        //when
        Book findBook = em.find(Book.class, book.getId());
        findBook.updateBuilder()
                .name("changeName")
                .price(11000)
                .stockQuantity(10)
                .build();
        em.flush();
        em.clear();

        //then
        Book updateBook = em.find(Book.class, book.getId());
        assertThat(updateBook.getName()).isEqualTo("changeName");
        assertThat(updateBook.getPrice()).isEqualTo(11000);
        assertThat(updateBook.getStockQuantity()).isEqualTo(10);
    }

    @Test
    public void addStockTest() throws Exception{
        //given
        Book book = Book.createBuilder()
                .name("test")
                .author("test")
                .build();
        em.persist(book);
        em.flush();
        em.clear();

        //when
        Book findBook = em.find(Book.class, book.getId());
        findBook.addStock(10);

        //then
        assertThat(findBook.getStockQuantity()).isEqualTo(10);
    }

    @Test
    public void removeStockTest() throws Exception{
        //given
        Book book = Book.createBuilder()
                .name("test")
                .author("test")
                .stockQuantity(5)
                .build();
        em.persist(book);
        em.flush();
        em.clear();

        //when
        Book findBook = em.find(Book.class, book.getId());
        findBook.removeStock(3);

        //then
        assertThat(findBook.getStockQuantity()).isEqualTo(2);
        assertThatThrownBy(() -> findBook.removeStock(100))
                .isInstanceOf(NotEnoghStockException.class)
                .hasMessageContaining("need");
    }

}