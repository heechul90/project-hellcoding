package com.heech.hellcoding.core.shop.item.book.domain;

import com.heech.hellcoding.core.shop.category.domain.Category;
import com.heech.hellcoding.core.shop.item.info.domain.Item;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

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
    /**
     * Book 생성
     */
    @Builder(builderClassName = "createBookBuilder", builderMethodName = "createBookBuilder")
    public Book(String name, String title, String content, int price, int stockQuantity, Category category, String author, String isbn) {
        super(name, title, content, price, stockQuantity, category);

        Assert.hasText(name, "name 필수값입니다.");
        Assert.notNull(category, "category 필수입니다.");
        Assert.hasText(author, "author 필수입니다.");

        this.author = author;
        this.isbn = isbn;
    }

    //===수정 메서드===//
    /**
     * Book 수정
     */
    @Builder(builderClassName = "updateBookBuilder", builderMethodName = "updateBookBuilder")
    public void updateBook(String name, String title, String content, int price, int stockQuantity, String author, String isbn, Category category) {
        updateItem(name, title, content, price, stockQuantity, category);
        if (hasText(author)) this.author = author;
        if (hasText(isbn)) this.isbn = isbn;
    }
}
