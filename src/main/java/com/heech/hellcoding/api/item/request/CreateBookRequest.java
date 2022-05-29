package com.heech.hellcoding.api.item.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
public class CreateBookRequest extends CreateItemRequest {

    private String author;
    private String isbn;
}
