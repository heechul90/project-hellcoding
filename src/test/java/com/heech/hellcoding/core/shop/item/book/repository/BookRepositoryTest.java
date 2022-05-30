package com.heech.hellcoding.core.shop.item.book.repository;

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

@SpringBootTest
@Transactional
class BookRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    BookRepository bookRepository;

    @Test
    public void findBooksTest() throws Exception{
        //given

        //when
        BookSearchCondition condition = new BookSearchCondition();
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Book> content = bookRepository.findBooks(condition, pageRequest);

        for (Book book : content.getContent()) {
            System.out.println("book.getName() = " + book.getName());
            System.out.println("book.getAuthor() = " + book.getAuthor());
        }

        //then
    }
}