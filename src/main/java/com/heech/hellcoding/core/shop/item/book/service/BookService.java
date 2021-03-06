package com.heech.hellcoding.core.shop.item.book.service;

import com.heech.hellcoding.core.category.domain.Category;
import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import com.heech.hellcoding.core.shop.item.book.domain.Book;
import com.heech.hellcoding.core.shop.item.book.dto.BookSearchCondition;
import com.heech.hellcoding.core.shop.item.book.repository.BookQueryRepository;
import com.heech.hellcoding.core.shop.item.book.repository.BookRepository;
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
public class BookService {

    private final BookRepository bookRepository;
    private final BookQueryRepository bookQueryRepository;

    /**
     * 상품 > Book 목록 조회
     */
    public Page<Book> findBooks(BookSearchCondition condition, Pageable pageable) {
        return bookQueryRepository.findBooks(condition, pageable);
    }

    /**
     * 상품 > Book 단건 조회
     */
    public Book findBook(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));
    }

    /**
     * 상품 > Book 저장
     */
    @Transactional
    public Long saveBook(Book book) {
        return bookRepository.save(book).getId();
    }

    /**
     * 상품 > Book 수정
     */
    @Transactional
    public void updateBook(Long id, String name, String title, String content, int price, int stockQuantity, Category category, String author, String isbn) {
        Book findBook = bookRepository.findById(id).orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));
        findBook.updateBookBuilder()
                .name(name)
                .title(title)
                .content(content)
                .price(price)
                .stockQuantity(stockQuantity)
                .category(category)
                .author(author)
                .isbn(isbn)
                .build();
    }

    /**
     * 상품 > Book 삭제
     */
    @Transactional
    public void deleteBook(Long id) {
        Book findBook = bookRepository.findById(id).orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));
        bookRepository.delete(findBook);
    }

}
