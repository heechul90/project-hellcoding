package com.heech.hellcoding.api.item.book;

import com.heech.hellcoding.core.common.json.JsonResult;
import com.heech.hellcoding.core.shop.item.book.domain.Book;
import com.heech.hellcoding.core.shop.item.book.dto.BookDto;
import com.heech.hellcoding.core.shop.item.book.dto.BookSearchCondition;
import com.heech.hellcoding.core.shop.item.book.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    //TODO 상품 > Book 저장
    //TODO 상품 > Book 수정
    //TODO 상품 > Book 삭제

}
