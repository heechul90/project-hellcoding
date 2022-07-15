package com.heech.hellcoding.core.category.repository;

import com.heech.hellcoding.core.category.domain.Category;
import com.heech.hellcoding.core.category.domain.ServiceSection;
import com.heech.hellcoding.core.category.dto.CategorySearchCondition;
import com.heech.hellcoding.core.common.dto.SearchCondition;
import org.assertj.core.api.Assertions;
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
public class CategoryQueryRepositoryTest {

    @PersistenceContext EntityManager em;

    @Autowired CategoryQueryRepository categoryQueryRepository;

    private Category getCategory(Category parent, ServiceSection serviceSection, int serialNumber, String name, String content) {
        Category category = Category.createCategoryBuilder()
                .parent(parent)
                .serviceSection(serviceSection)
                .serialNumber(serialNumber)
                .name(name)
                .content(content)
                .build();
        em.persist(category);
        return category;
    }

    @Test
    public void findCategoriesTest() throws Exception{
        //given
        Category bookCategory = getCategory(null, ServiceSection.SHOP, 0, "book", "book");
        Category albumCategory = getCategory(null, ServiceSection.SHOP, 1, "album", "album");
        Category developCategory = getCategory(bookCategory, ServiceSection.SHOP, 0, "develop", "develop");
        Category languageCategory = getCategory(bookCategory, ServiceSection.SHOP, 1, "language", "language");
        Category kpopCategory = getCategory(albumCategory, ServiceSection.SHOP, 0, "k-pop", "k-pop");
        Category balladCategory = getCategory(albumCategory, ServiceSection.SHOP, 1, "ballad", "ballad");

        //when
        CategorySearchCondition condition = new CategorySearchCondition();
        condition.setSearchServiceSection(ServiceSection.SHOP);
        condition.setSearchCondition(SearchCondition.NAME);
        condition.setSearchKeyword("boo");
        PageRequest pageRequest = PageRequest.of(0, Integer.MAX_VALUE);
        Page<Category> content = categoryQueryRepository.findCategories(condition, pageRequest);

        //then
        Assertions.assertThat(content.getTotalElements()).isEqualTo(1);
    }
}
