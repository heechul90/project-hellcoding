package com.heech.hellcoding.api.shop.orderItem;

import com.heech.hellcoding.api.shop.orderItem.request.CreateOrderItemRequest;
import com.heech.hellcoding.api.shop.orderItem.request.UpdateOrderItemRequest;
import com.heech.hellcoding.api.shop.orderItem.response.CreateOrderItemResponse;
import com.heech.hellcoding.api.shop.orderItem.response.UpdateOrderItemResponse;
import com.heech.hellcoding.core.common.json.JsonResult;
import com.heech.hellcoding.core.shop.orderItem.domain.OrderItem;
import com.heech.hellcoding.core.shop.orderItem.dto.OrderItemDto;
import com.heech.hellcoding.core.shop.orderItem.dto.OrderItemSearchCondition;
import com.heech.hellcoding.core.shop.orderItem.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/shop/orderItems")
public class ApiOrderItemController {

    private final OrderItemService orderItemService;

    /**
     * 주문상품 목록 조회
     */
    @GetMapping
    public JsonResult findOrderItems(OrderItemSearchCondition condition, Pageable pageable) {
        Page<OrderItem> content = orderItemService.findOrderItems(condition, pageable);
        List<OrderItemDto> collect = content.getContent().stream()
                .map(orderItem -> new OrderItemDto(
                        orderItem.getId(),
                        orderItem.getItem().getName(),
                        orderItem.getItem().getPrice(),
                        orderItem.getOrderPrice(),
                        orderItem.getCount()
                ))
                .collect(Collectors.toList());
        return JsonResult.OK(collect);
    }

    /**
     * 주문상품 단건 조회
     */
    @GetMapping(value = "/{orderItemId}")
    public JsonResult findOrderItem(@PathVariable("orderItemId") Long orderItemId) {
        OrderItem findOrderItem = orderItemService.findOrderItem(orderItemId);
        OrderItemDto orderItem = new OrderItemDto (
                findOrderItem.getId(),
                findOrderItem.getItem().getName(),
                findOrderItem.getItem().getPrice(),
                findOrderItem.getOrderPrice(),
                findOrderItem.getCount()
        );
        return JsonResult.OK(orderItem);
    }

    /**
     * 주문상품 추가
     */
    @PostMapping(value = "/{orderId}/add")
    public JsonResult addOrderItem(@PathVariable("orderId") Long orderId,
                                   @Validated @RequestBody CreateOrderItemRequest request, BindingResult bindingResult) {

        /*if (bindingResult.hasErrors()) {
            log.info("bindingResult = {}", bindingResult);
            return JsonResult.ERROR(bindingResult.getAllErrors());
        }*/
        Long addedId = orderItemService.addOrderItem(orderId, request.getItemId(), request.getCount());
        return JsonResult.OK(new CreateOrderItemResponse(addedId));
    }

    /**
     * 주문상품 수정
     */
    @PutMapping(value = "/{orderItemId}")
    public JsonResult updateOrderItem(@PathVariable("orderItemId") Long orderItemId,
                                      @Validated @RequestBody UpdateOrderItemRequest request, BindingResult bindingResult) {

        /*if (bindingResult.hasErrors()) {
            log.info("bindingResult = {}", bindingResult);
            return JsonResult.ERROR(bindingResult.getAllErrors());
        }*/

        orderItemService.updateOrderItem(orderItemId, request.getOrderPrice(), request.getOrderCount());
        OrderItem findOrderItem = orderItemService.findOrderItem(orderItemId);
        return JsonResult.OK(new UpdateOrderItemResponse(findOrderItem.getId()));
    }

    /**
     * 주문상품 삭제
     */
    @DeleteMapping(value = "/{orderItemId}")
    public JsonResult deleteOrderItem(@PathVariable("orderItemId") Long orderItemId) {
        orderItemService.deleteOrderItem(orderItemId);
        return JsonResult.OK();
    }
}
