package com.heech.hellcoding.api.item.book;

import com.heech.hellcoding.core.shop.item.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ApiBookController {


    private final BookRepository bookRepository;
}
