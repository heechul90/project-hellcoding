package com.heech.hellcoding.core.shop.category.repository;

import com.heech.hellcoding.core.shop.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryQuerydsl {
}
