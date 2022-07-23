package com.heech.hellcoding.api.category;

import com.heech.hellcoding.api.category.request.CreateCategoryRequest;
import com.heech.hellcoding.api.category.request.UpdateCategoryRequest;
import com.heech.hellcoding.api.category.response.CreateCategoryResponse;
import com.heech.hellcoding.api.category.response.UpdateCategoryResponse;
import com.heech.hellcoding.core.category.domain.Category;
import com.heech.hellcoding.core.category.dto.CategorySearchCondition;
import com.heech.hellcoding.core.category.service.CategoryService;
import com.heech.hellcoding.core.common.json.JsonResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

        return JsonResult.OK(content);
    }

    /**
     * 카테고리 단건 조회
     */
    @GetMapping(value = "/{id}")
    public JsonResult findCategory(@PathVariable("id") Long categoryId) {
        Category findCategory = categoryService.findCategory(categoryId);
        return JsonResult.OK(findCategory);
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

        Category category = Category.createCategoryBuilder()
                .parent(categoryService.findCategory(request.getUpperCategoryId()))
                .serviceName(request.getServiceName())
                .serialNumber(request.getCategorySerialNumber())
                .name(request.getCategoryName())
                .content(request.getCategoryContent())
                .build();
        Long savedCategoryId = categoryService.saveCategory(category);

        return JsonResult.OK(new CreateCategoryResponse(savedCategoryId));
    }

    /**
     * 카테고리 수정
     */
    @PutMapping(value = "/{id}")
    public JsonResult updateCategory(@PathVariable("id") Long categoryId,
                                     @RequestBody @Validated UpdateCategoryRequest request, BindingResult bindingResult) {

        //TODO validation check
        if (bindingResult.hasErrors()) {
            return JsonResult.ERROR(bindingResult.getAllErrors());
        }

        categoryService.updateCategory(
                categoryId,
                request.getUpperCategoryId(),
                request.getServiceName(),
                request.getCategorySerialNumber(),
                request.getCategoryName(),
                request.getCategoryContent());
        Category category = categoryService.findCategory(categoryId);
        return JsonResult.OK(new UpdateCategoryResponse(category.getId()));
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
