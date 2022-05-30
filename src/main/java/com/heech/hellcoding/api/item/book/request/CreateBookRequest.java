package com.heech.hellcoding.api.item.book.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateBookRequest {

    private String author;
    private String isbn;
}
