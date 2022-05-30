package com.heech.hellcoding.core.shop.item.book.repository;

import com.heech.hellcoding.core.shop.item.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryQuerydsl {

    List<Book> findByName(String name);

    List<Book> findByAuthor(String author);
}
