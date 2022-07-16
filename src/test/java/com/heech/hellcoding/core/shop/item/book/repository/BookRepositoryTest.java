package com.heech.hellcoding.core.shop.item.book.repository;

import com.heech.hellcoding.core.category.domain.Category;
import com.heech.hellcoding.core.category.domain.ServiceName;
import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import com.heech.hellcoding.core.shop.item.book.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {

    @PersistenceContext EntityManager em;

    @Autowired BookRepository bookRepository;

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
    public void findByXxxTest() {
        //given
        Category category = getCategory();

        Book book1 = Book.createBookBuilder()
                .name("상품이름")
                .title("상품타이틀1")
                .content("상품내용1")
                .price(7200)
                .stockQuantity(150)
                .category(category)
                .author("저자")
                .isbn(UUID.randomUUID().toString().toUpperCase())
                .build();

        Book book2 = Book.createBookBuilder()
                .name("상품이름")
                .title("상품타이틀2")
                .content("상품내용2")
                .price(7200)
                .stockQuantity(200)
                .category(category)
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