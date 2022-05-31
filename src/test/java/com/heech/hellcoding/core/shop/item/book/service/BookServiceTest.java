package com.heech.hellcoding.core.shop.item.book.service;

import com.heech.hellcoding.core.shop.item.book.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BookServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    BookService bookService;

    @Test
    void findBooks() {
    }

    @Test
    void findBook() {
    }

    @Test
    void saveBook() {
    }

    @Test
    void updateBook() {
        //given
        Book book = Book.createBuilder()
                .name("test")
                .title("test")
                .content("test")
                .price(10000)
                .stockQuantity(10)
                .author("test")
                .isbn("test")
                .build();
        Long savedId = bookService.saveBook(book);
        em.flush();
        em.clear();

        //when
        String name = "";
        String title = "";
        String content = "";
        int price = 100;
        int stockQuantity = 100;
        String author = "changeAuthor";
        String isbn = "";
        bookService.updateBook(savedId, name, title, content, price, stockQuantity, author, isbn);
        em.flush();
        em.clear();

        //then
        Book updateBook = bookService.findBook(savedId);
        assertThat(updateBook.getName()).isEqualTo("test");
        assertThat(updateBook.getPrice()).isEqualTo(100);
        assertThat(updateBook.getStockQuantity()).isEqualTo(100);
        assertThat(updateBook.getAuthor()).isEqualTo("changeAuthor");
    }

    @Test
    void deleteBook() {
    }
}