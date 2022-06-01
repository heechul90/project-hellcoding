package com.heech.hellcoding.api.item.book;

import com.heech.hellcoding.api.item.book.request.CreateBookRequest;
import com.heech.hellcoding.core.common.json.JsonResult;
import com.heech.hellcoding.core.shop.item.book.domain.Book;
import com.heech.hellcoding.core.shop.item.book.dto.BookDto;
import com.heech.hellcoding.core.shop.item.book.dto.BookSearchCondition;
import com.heech.hellcoding.core.shop.item.book.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/item/books")
public class ApiBookController {

    private final BookService bookService;

    /**
     * 상품 > Book 목록 조회
     */
    @GetMapping
    public JsonResult findBooks(BookSearchCondition condition, Pageable pageable) {
        Page<Book> content = bookService.findBooks(condition, pageable);
        List<BookDto> collect = content.getContent().stream()
                .map(book -> new BookDto(
                        book.getName(),
                        book.getTitle(),
                        book.getContent(),
                        book.getPrice(),
                        book.getStockQuantity(),
                        book.getAuthor(),
                        book.getIsbn()
                ))
                .collect(Collectors.toList());
        return JsonResult.OK(collect);
    }

    /**
     * 상품 > Book 단건 조회
     */
    @GetMapping(value = "/{id}")
    public JsonResult findBook(@PathVariable("id") Long id) {
        Book findBook = bookService.findBook(id);
        BookDto book = new BookDto(
                findBook.getName(),
                findBook.getTitle(),
                findBook.getContent(),
                findBook.getPrice(),
                findBook.getStockQuantity(),
                findBook.getAuthor(),
                findBook.getIsbn()
        );
        return JsonResult.OK(book);
    }

    /**
     * 상품 > Book 저장
     */
    @PostMapping
    public JsonResult saveBook(@RequestBody @Validated CreateBookRequest request, BindingResult bindingResult) throws BindException {

        //TODO validation check
        if (bindingResult.hasErrors()) {
            return JsonResult.ERROR(bindingResult.getAllErrors());
        }

        Book book = Book.createBuilder()
                .name(request.getItemName())
                .title(request.getItemTitle())
                .content(request.getItemContent())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .build();
        Long savedId = bookService.saveBook(book);
        return JsonResult.OK(new HashMap<String, Long>() {{
            put("savedId", savedId);
        }});
    }
    //TODO 상품 > Book 수정
    //TODO 상품 > Book 삭제

}
