package com.heech.hellcoding.core.shop.item.book.dto;

import com.heech.hellcoding.core.shop.item.info.dto.ItemDto;
import lombok.Getter;

@Getter
public class BookDto extends ItemDto {

    private String author;
    private String isbn;

    public BookDto(String itemName, String itemTitle, String itemContent, int price, int stockQuantity, String author, String isbn) {
        super(itemName, itemTitle, itemContent, price, stockQuantity);
        this.author = author;
        this.isbn = isbn;
    }
}
