package com.heech.hellcoding.core.shop.item.book.service;

import com.heech.hellcoding.core.common.dto.SearchCondition;
import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import com.heech.hellcoding.core.shop.item.book.domain.Book;
import com.heech.hellcoding.core.shop.item.book.dto.BookSearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class BookServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    BookService bookService;

    @Test
    void findBooks() {
        //given
        for (int i = 0; i < 50; i++) {
            bookService.saveBook(
                    Book.createBookBuilder()
                            .name("book" + i)
                            .title("book" + i)
                            .content("book" + i)
                            .price(1000 + i)
                            .stockQuantity(10 + i)
                            .author("book" + i)
                            .isbn(UUID.randomUUID().toString().toUpperCase())
                            .build()
            );
        }

        BookSearchCondition condition = new BookSearchCondition();
        condition.setSearchCondition(SearchCondition.NAME);
        condition.setSearchKeyword("boo");

        PageRequest pageRequest = PageRequest.of(0, 10);

        //when
        Page<Book> content = bookService.findBooks(condition, pageRequest);

        //then
        assertThat(content.getContent().size()).isEqualTo(10);
        assertThat(content.getContent()).extracting("name").contains("book1", "book9");
    }

    @Test
    void findBook() {
        //given
        Book book = Book.createBookBuilder()
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
        Book findBook = bookService.findBook(savedId);

        //then
        assertThat(findBook.getName()).isEqualTo("test");
        assertThat(findBook.getPrice()).isEqualTo(10000);
    }

    @Test
    void saveBook() {
        //given
        Book book = Book.createBookBuilder()
                .name("test")
                .title("test")
                .content("test")
                .price(10000)
                .stockQuantity(10)
                .author("test")
                .isbn("test")
                .build();

        //when
        Long savedId = bookService.saveBook(book);
        em.flush();
        em.clear();

        //then
        Book findBook = bookService.findBook(savedId);
        assertThat(findBook.getName()).isEqualTo("test");
        assertThat(findBook.getPrice()).isEqualTo(10000);
    }

    @Test
    void updateBook() {
        //given
        Book book = Book.createBookBuilder()
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
        //given
        Book book = Book.createBookBuilder()
                .name("test")
                .title("test")
                .content("test")
                .price(10000)
                .stockQuantity(10)
                .author("test")
                .isbn("test")
                .build();
        Long savedId = bookService.saveBook(book);

        //when
        bookService.deleteBook(savedId);

        //then
        assertThatThrownBy(() -> bookService.findBook(savedId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("조회에");
    }
}