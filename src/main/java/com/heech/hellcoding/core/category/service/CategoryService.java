package com.heech.hellcoding.core.category.service;

import com.heech.hellcoding.core.category.domain.Category;
import com.heech.hellcoding.core.category.domain.ServiceName;
import com.heech.hellcoding.core.category.dto.CategorySearchCondition;
import com.heech.hellcoding.core.category.repository.CategoryQueryRepository;
import com.heech.hellcoding.core.category.repository.CategoryRepository;
import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryQueryRepository categoryQueryRepository;

    /**
     * 카테고리 목록 조회
     */
    public Page<Category> findCategories(CategorySearchCondition condition, Pageable pageable) {
        return categoryQueryRepository.findCategories(condition, pageable);
    }


    /**
     * 카테고리 단건 조회
     */
    public Category findCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NoSuchElementException("잘못된 접근입니다."));
    }

    /**
     * 카테고리 저장
     */
    @Transactional
    public Long saveCategory(Category category) {
        return categoryRepository.save(category).getId();
    }

    /**
     * 카테고리 수정
     */
    @Transactional
    public void updateCategory(Long categoryId, Long upperCategoryId, ServiceName serviceName, Integer categorySerialNumber, String categoryName, String categoryContent) {
        Category findCategory = categoryRepository.findById(categoryId).orElseThrow(() -> new NoSuchElementException("잘못된 접근입니다."));
        findCategory.updateCategoryBuilder()
                .parent(upperCategoryId != null ? categoryRepository.findById(upperCategoryId).orElse(null) : null)
                .serviceName(serviceName)
                .serialNumber(categorySerialNumber)
                .name(categoryName)
                .content(categoryContent)
                .build();
    }

    /**
     * 카테고리 삭제
     */
    @Transactional
    public void deleteCategory(Long id) {
        Category findCategory = categoryRepository.findById(id).orElseThrow(() -> new NoSuchElementException("잘못된 접근이니다."));
        categoryRepository.delete(findCategory);
    }
}
