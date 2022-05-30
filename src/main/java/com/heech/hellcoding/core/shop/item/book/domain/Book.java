package com.heech.hellcoding.core.shop.item.book.domain;

import com.heech.hellcoding.core.shop.item.domain.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "BOOK")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book extends Item {

    private String author;
    private String isbn;

}
