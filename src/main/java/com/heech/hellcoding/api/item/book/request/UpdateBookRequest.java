package com.heech.hellcoding.api.item.book.request;

import com.heech.hellcoding.api.item.info.request.UpdateItemRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class UpdateBookRequest extends UpdateItemRequest {

    @Size(max = 60)
    private String author;

    private String isbn;
}
