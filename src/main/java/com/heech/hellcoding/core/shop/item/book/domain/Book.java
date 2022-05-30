package com.heech.hellcoding.core.shop.item.book.domain;

import com.heech.hellcoding.core.shop.item.common.domain.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "BOOK")
@Getter
@NoArgsConstructor
public class Book extends Item {

    private String author;
    private String isbn;

    @Builder
    public Book(String name, String title, String content, int price, int stockQuantity, String author, String isbn) {
        super(name, title, content, price, stockQuantity);
        this.author = author;
        this.isbn = isbn;
    }


}
