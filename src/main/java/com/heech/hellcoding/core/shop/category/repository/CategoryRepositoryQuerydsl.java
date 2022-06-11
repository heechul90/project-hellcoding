package com.heech.hellcoding.core.shop.category.repository;

import com.heech.hellcoding.core.shop.category.domain.Category;
import com.heech.hellcoding.core.shop.category.dto.CategorySearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryRepositoryQuerydsl {

    Page<Category> findCategories(CategorySearchCondition condition, Pageable pageable);
}
