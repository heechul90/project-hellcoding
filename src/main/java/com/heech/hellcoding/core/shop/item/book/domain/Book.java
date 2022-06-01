package com.heech.hellcoding.core.shop.item.book.domain;

import com.heech.hellcoding.core.shop.item.info.domain.Item;
import com.mysema.commons.lang.Assert;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import static org.springframework.util.StringUtils.*;

@Entity
@DiscriminatorValue(value = "BOOK")
@Getter
@NoArgsConstructor
public class Book extends Item {

    private String author;
    private String isbn;

    //=== 생성 메서드===//
    @Builder(builderMethodName = "createBuilder")
    public Book(String name, String title, String content, int price, int stockQuantity, String author, String isbn) {
        super(name, title, content, price, stockQuantity);

        Assert.hasText(name, "name은 필수값입니다.");
        Assert.isTrue(price >= 0, "price은 양수여야합니다.");
        Assert.isTrue(stockQuantity >= 0, "stockQuantity은 양수여야합니다.");
        Assert.hasText(author, "author은 필수값입니다.");

        this.author = author;
        this.isbn = isbn;
    }

    //===변경 메서드===//
    @Builder(builderMethodName = "updateBuilder")
    public void updateBook(String name, String title, String content, int price, int stockQuantity, String author, String isbn) {
        updateItem(name, title, content, price, stockQuantity);
        if (hasText(author)) {
            this.author = author;
        }
        if (hasText(isbn)) {
            this.isbn = isbn;
        }
    }
}
