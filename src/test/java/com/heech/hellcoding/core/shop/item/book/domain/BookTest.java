package com.heech.hellcoding.core.shop.item.book.domain;

import com.heech.hellcoding.core.category.domain.Category;
import com.heech.hellcoding.core.category.domain.ServiceName;
import com.heech.hellcoding.core.common.exception.NotEnoghStockException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookTest {

    @PersistenceContext EntityManager em;

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

    private Book getBook(Category category) {
        Book book = Book.createBookBuilder()
                .name("book_name")
                .title("book_title")
                .content("book_content")
                .price(0)
                .stockQuantity(100)
                .category(category)
                .author("book_author")
                .isbn("book_isbn")
                .build();
        return book;
    }

    @Test
    public void createBookTest() {
        //given
        Category category = getCategory();
        Book book = getBook(category);

        //when
        em.persist(book);
        em.flush();
        em.clear();

        //then
        Book findBook = em.find(Book.class, book.getId());
        assertThat(findBook.getName()).isEqualTo("book_name");
        assertThat(findBook.getAuthor()).isEqualTo("book_author");
        assertThat(findBook.getPrice()).isZero();
        assertThat(findBook.getCategory().getName()).isEqualTo("category_name");
    }

    @Test
    public void updateBookTest() {
        //given
        Category category = getCategory();
        Book book = getBook(category);
        em.persist(book);
        em.flush();
        em.clear();


        //when
        Book findBook = em.find(Book.class, book.getId());
        findBook.updateBookBuilder()
                .name("update_category_name")
                .price(11000)
                .stockQuantity(10)
                .build();
        em.flush();
        em.clear();

        //then
        Book updateBook = em.find(Book.class, book.getId());
        assertThat(updateBook.getName()).isEqualTo("update_category_name");
        assertThat(updateBook.getPrice()).isEqualTo(11000);
        assertThat(updateBook.getStockQuantity()).isEqualTo(10);
    }

    @Test
    public void addStockTest() {
        //given
        Category category = getCategory();
        Book book = getBook(category);
        em.persist(book);
        em.flush();
        em.clear();

        //when
        Book findBook = em.find(Book.class, book.getId());
        findBook.addStock(10);

        //then
        assertThat(findBook.getStockQuantity()).isEqualTo(110);
    }

    @Test
    public void removeStockTest() {
        //given
        Category category = getCategory();
        Book book = getBook(category);
        em.persist(book);
        em.flush();
        em.clear();

        //when
        Book findBook = em.find(Book.class, book.getId());
        findBook.removeStock(10);

        //then
        assertThat(findBook.getStockQuantity()).isEqualTo(90);
        assertThatThrownBy(() -> findBook.removeStock(100))
                .isInstanceOf(NotEnoghStockException.class)
                .hasMessageContaining("need");
    }

}