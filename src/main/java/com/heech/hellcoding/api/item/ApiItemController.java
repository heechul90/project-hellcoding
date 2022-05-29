package com.heech.hellcoding.api.item;

import com.heech.hellcoding.api.item.request.CreateBookRequest;
import com.heech.hellcoding.api.item.request.CreateItemRequest;
import com.heech.hellcoding.api.item.response.CreateItemResponse;
import com.heech.hellcoding.core.common.json.JsonResult;
import com.heech.hellcoding.core.shop.item.domain.Book;
import com.heech.hellcoding.core.shop.item.domain.Item;
import com.heech.hellcoding.core.shop.item.dto.ItemDto;
import com.heech.hellcoding.core.shop.item.dto.ItemSearchCondition;
import com.heech.hellcoding.core.shop.item.repository.ItemRepository;
import com.heech.hellcoding.core.shop.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/items")
public class ApiItemController {

    private final ItemService itemService;
    private final ItemRepository itemRepository;

    /**
     * 상품 조회(목록)
     */
    @GetMapping
    public JsonResult findItems(ItemSearchCondition condition, Pageable pageable) {
        Page<Item> content = itemService.findItems(condition, pageable);
        List<ItemDto> collect = content.getContent().stream()
                .map(item -> new ItemDto(
                        item.getName(),
                        item.getTitle(),
                        item.getContent(),
                        item.getPrice(),
                        item.getStockQuantity()
                ))
                .collect(Collectors.toList());
        return JsonResult.OK(collect);
    }

    /**
     * 상품 조회(단건)
     */
    @GetMapping(value = "/{id}")
    public JsonResult findItem(@PathVariable("id") Long id) {
        Item findItem = itemRepository.findById(id).orElse(null);
        ItemDto item = new ItemDto(
                findItem.getName(),
                findItem.getTitle(),
                findItem.getContent(),
                findItem.getPrice(),
                findItem.getStockQuantity()
        );
        return JsonResult.OK(item);
    }

    /**
     * 상품 저장
     */
    @PostMapping(value = "/book")
    public JsonResult saveItem(@RequestBody @Validated CreateBookRequest request) {
        //TODO error return 처리 필요
        System.out.println("ApiItemController.saveItemmmmmm");

        Book book = new Book(request.getAuthor(), request.getIsbn());
        book.createItem(
                request.getItemName(),
                request.getItemTitle(),
                request.getItemContent(),
                request.getPrice(),
                request.getStockQuantity()
        );
        Item savedItem = itemService.save(book);
        return JsonResult.OK(new CreateItemResponse(savedItem.getId()));
    }
    //TODO 상품 수정
    //TODO 상품 삭제


}
