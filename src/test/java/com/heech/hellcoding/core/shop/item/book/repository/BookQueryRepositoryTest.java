package com.heech.hellcoding.core.shop.item.book.repository;

import com.heech.hellcoding.core.category.domain.Category;
import com.heech.hellcoding.core.category.domain.ServiceName;
import com.heech.hellcoding.core.common.dto.SearchCondition;
import com.heech.hellcoding.core.shop.ShopTestConfig;
import com.heech.hellcoding.core.shop.item.book.domain.Book;
import com.heech.hellcoding.core.shop.item.book.dto.BookSearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(ShopTestConfig.class)
class BookQueryRepositoryTest {

    @PersistenceContext EntityManager em;

    @Autowired BookQueryRepository bookQueryRepository;

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
    void findBooksTest() {
        //given
        Category category = getCategory();

        for (int i = 0; i <50; i++) {
            em.persist(
                    Book.createBookBuilder()
                            .name("상품이름" + i)
                            .title("상품타이틀" + i)
                            .content("상품내용" + i)
                            .price(7200 + i)
                            .stockQuantity(200 + i)
                            .category(category)
                            .author("저자" + i)
                            .isbn(UUID.randomUUID().toString().toUpperCase())
                            .build()
            );
        }

        //when
        BookSearchCondition condition = new BookSearchCondition();
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Book> content1 = bookQueryRepository.findBooks(condition, pageRequest);

        //then
        assertThat(content1.getContent().size()).isEqualTo(10);

        //when
        condition.setSearchCondition(SearchCondition.NAME);
        condition.setSearchKeyword("이름");
        condition.setSearchPriceGoe(7245);
        Page<Book> content2 = bookQueryRepository.findBooks(condition, pageRequest);

        //then
        assertThat(content2.getContent().size()).isEqualTo(5);
        assertThat(content2.getContent())
                .extracting("name")
                .containsExactly("상품이름45", "상품이름46", "상품이름47", "상품이름48", "상품이름49");
    }
}