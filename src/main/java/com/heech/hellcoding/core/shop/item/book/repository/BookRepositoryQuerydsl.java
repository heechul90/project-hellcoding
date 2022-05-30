package com.heech.hellcoding.core.shop.item.book.repository;

import com.heech.hellcoding.core.shop.item.book.domain.Book;
import com.heech.hellcoding.core.shop.item.book.dto.BookSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookRepositoryQuerydsl {

    Page<Book> findBooks(BookSearchCondition condition, Pageable pageable);
}
