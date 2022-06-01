package com.heech.hellcoding.api.item.book.request;

import com.heech.hellcoding.api.item.info.request.CreateItemRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CreateBookRequest extends CreateItemRequest {

    @NotEmpty
    @Size(max = 60)
    private String author;

    private String isbn;

}
