package com.heech.hellcoding.api.category;

import com.heech.hellcoding.api.category.request.CreateCategoryRequest;
import com.heech.hellcoding.api.category.request.UpdateCategoryRequest;
import com.heech.hellcoding.api.category.response.CreateCategoryResponse;
import com.heech.hellcoding.api.category.response.UpdateCategoryResponse;
import com.heech.hellcoding.core.common.json.JsonResult;
import com.heech.hellcoding.core.shop.category.domain.Category;
import com.heech.hellcoding.core.shop.category.dto.CategoryDto;
import com.heech.hellcoding.core.shop.category.dto.CategorySearchCondition;
import com.heech.hellcoding.core.shop.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/categories")
public class ApiCategoryController {

    private final CategoryService categoryService;

    /**
     * 카테고리 목록 조회
     */
    @GetMapping
    public JsonResult findCategories(CategorySearchCondition condition, Pageable Pageable) {
        Page<Category> content = categoryService.findCategories(condition, Pageable);
        /*List<CategoryDto> collect = content.getContent().stream()
                .filter(category -> category.getParent() == null)
                .map(category -> new CategoryDto(
                        category.getId(),
                        category.getName(),
                        category.getCategoryOrder(),
                        category.getIsActivate(),
                        null,
                        category.getCreatedDate(),
                        category.getCreatedBy()
                ))
                .collect(Collectors.toList());*/

        List<CategoryDto> collect = content.getContent().stream()
                .map(category -> new CategoryDto(
                        category.getId(),
                        category.getName(),
                        category.getCategoryOrder(),
                        category.getIsActivate(),
                        category.getParent() != null ? new CategoryDto(category.getParent().getId(), category.getParent().getName(), category.getParent().getCategoryOrder()) : null,
                        category.getCreatedDate(),
                        category.getCreatedBy()
                ))
                .collect(Collectors.toList());
        return JsonResult.OK(collect);
    }

    /**
     * 카테고리 단건 조회
     */
    @GetMapping(value = "/{id}")
    public JsonResult findCategory(@PathVariable("id") Long categoryId) {
        Category findCategory = categoryService.findCategory(categoryId);
        CategoryDto category = new CategoryDto(
                findCategory.getId(),
                findCategory.getName(),
                findCategory.getCategoryOrder(),
                findCategory.getIsActivate(),
                findCategory.getParent() != null ? new CategoryDto(
                        findCategory.getParent().getId(),
                        findCategory.getParent().getName(),
                        findCategory.getParent().getCategoryOrder()
                ) : null,
                findCategory.getCreatedDate(),
                findCategory.getCreatedBy()
        );
        return JsonResult.OK(category);
    }

    /**
     * 카테고리 저장
     */
    @PostMapping
    public JsonResult saveCategory(@RequestBody @Validated CreateCategoryRequest request, BindingResult bindingResult) {

        //TODO validation check
        if (bindingResult.hasErrors()) {
            return JsonResult.ERROR(bindingResult.getAllErrors());
        }

        Category category;
        if (request.getParentId() == null) {
            category = Category.createRootCategoryBuilder()
                    .name(request.getCategoryName())
                    .categoryOrder(request.getCategoryOrder())
                    .build();
        } else {
            category = Category.createChildCategoryBuilder()
                    .parent(categoryService.findCategory(request.getParentId()))
                    .name(request.getCategoryName())
                    .categoryOrder(request.getCategoryOrder())
                    .build();
        }
        Long savedId = categoryService.saveCategory(category);

        return JsonResult.OK(new CreateCategoryResponse(savedId));
    }

    /**
     * 카테고리 수정
     */
    @PutMapping(value = "/{id}")
    public JsonResult updateCategory(@PathVariable("id") Long categoryid,
                                     @RequestBody @Validated UpdateCategoryRequest request, BindingResult bindingResult) {

        //TODO validation check
        if (bindingResult.hasErrors()) {
            return JsonResult.ERROR(bindingResult.getAllErrors());
        }

        categoryService.updateCategory(categoryid, request.getCategoryName(), request.getCategoryOrder());
        Category category = categoryService.findCategory(categoryid);
        return JsonResult.OK(new UpdateCategoryResponse(category.getId()));
    }

    /**
     * 카테고리 활성화
     */
    @PutMapping(value = "/{id}/activate")
    public JsonResult activateCategory(@PathVariable("id") Long categoryId) {
        categoryService.activateCategory(categoryId);
        return JsonResult.OK();
    }

    /**
     * 카테고리 비활성화
     */
    @PutMapping(value = "/{id}/deactivate")
    public JsonResult deactivateCategory(@PathVariable("id") Long categoryId) {
        categoryService.deactivateCategory(categoryId);
        return JsonResult.OK();
    }

    /**
     * 카테고리 삭제
     */
    @DeleteMapping(value = "/{id}")
    public JsonResult deleteCategory(@PathVariable("id") Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return JsonResult.OK();
    }

}
