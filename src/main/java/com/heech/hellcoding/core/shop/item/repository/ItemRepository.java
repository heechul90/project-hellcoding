package com.heech.hellcoding.core.shop.item.repository;

import com.heech.hellcoding.core.shop.item.domain.Book;
import com.heech.hellcoding.core.shop.item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemReposityQuerydsl {

    List<Item> findByName(String name);

    /**
     * Book 테이블 조회는 리턴은 Book으로 받아야함
     */
    List<Book> findByAuthor(String author);

}
