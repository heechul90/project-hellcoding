package com.heech.hellcoding.core.shop.item.book.repository;

import com.heech.hellcoding.core.common.dto.SearchCondition;
import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import com.heech.hellcoding.core.shop.item.book.domain.Book;
import com.heech.hellcoding.core.shop.item.book.dto.BookSearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

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
        for (int i = 0; i <50; i++) {
            bookRepository.save(
                    Book.createBookBuilder()
                            .name("상품이름" + i)
                            .title("상품타이틀" + i)
                            .content("상품내용" + i)
                            .price(7200 + i)
                            .stockQuantity(200 + i)
                            .author("저자" + i)
                            .isbn(UUID.randomUUID().toString().toUpperCase())
                            .build()
            );
        }

        //when
        BookSearchCondition condition = new BookSearchCondition();
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Book> content1 = bookRepository.findBooks(condition, pageRequest);

        //then
        assertThat(content1.getContent().size()).isEqualTo(10);

        //when
        condition.setSearchCondition(SearchCondition.NAME);
        condition.setSearchKeyword("이름");
        condition.setSearchPriceGoe(7245);
        Page<Book> content2 = bookRepository.findBooks(condition, pageRequest);

        //then
        assertThat(content2.getContent().size()).isEqualTo(5);
        assertThat(content2.getContent())
                .extracting("name")
                .containsExactly("상품이름45", "상품이름46", "상품이름47", "상품이름48", "상품이름49");
    }

    @Test
    public void findByXxxTest() throws Exception{
        //given
        Book book1 = Book.createBookBuilder()
                .name("상품이름")
                .title("상품타이틀1")
                .content("상품내용1")
                .price(7200)
                .stockQuantity(150)
                .author("저자")
                .isbn(UUID.randomUUID().toString().toUpperCase())
                .build();

        Book book2 = Book.createBookBuilder()
                .name("상품이름")
                .title("상품타이틀2")
                .content("상품내용2")
                .price(7200)
                .stockQuantity(200)
                .author("저자")
                .isbn(UUID.randomUUID().toString().toUpperCase())
                .build();

        bookRepository.save(book1);
        bookRepository.save(book2);

        //when
        List<Book> resultList1 = bookRepository.findByName("상품이름");
        List<Book> resultList2 = bookRepository.findByAuthor("저자");
        Book findBook = bookRepository.findById(book1.getId()).orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));

        //then
        assertThat(resultList1).extracting("title").containsExactly("상품타이틀1", "상품타이틀2");
        assertThat(resultList2).extracting("content").containsExactly("상품내용1", "상품내용2");
        assertThat(findBook).isEqualTo(book1);
    }
}