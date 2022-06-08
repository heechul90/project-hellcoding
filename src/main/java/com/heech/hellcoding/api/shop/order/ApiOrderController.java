package com.heech.hellcoding.api.shop.order;

import com.heech.hellcoding.api.shop.order.request.CreateOrderRequest;
import com.heech.hellcoding.api.shop.order.request.ItemInfo;
import com.heech.hellcoding.api.shop.order.request.UpdateOrderRequest;
import com.heech.hellcoding.api.shop.order.response.CreateOrderResponse;
import com.heech.hellcoding.core.common.json.JsonResult;
import com.heech.hellcoding.core.shop.order.domain.Order;
import com.heech.hellcoding.core.shop.order.domain.OrderStatus;
import com.heech.hellcoding.core.shop.order.dto.OrderDto;
import com.heech.hellcoding.core.shop.order.dto.OrderSearchCondition;
import com.heech.hellcoding.core.shop.order.service.OrderService;
import com.heech.hellcoding.core.shop.orderItem.dto.OrderItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/orders")
public class ApiOrderController {

    private final OrderService orderService;

    /**
     * 주문 목록 조회
     */
    @GetMapping
    public JsonResult findOrders(OrderSearchCondition condition, Pageable pageable) {
        Page<Order> content = orderService.findOrders(condition, pageable);
        List<OrderDto> collect = content.getContent().stream()
                .map(order -> new OrderDto(
                        order.getId(),
                        order.getOrderDate(),
                        OrderStatus.ORDER.equals(order.getStatus()) ? "주문" : "주문취소",
                        order.getDelivery().getAddress().fullAddress(),
                        order.getOrderItems().stream()
                                .map(orderItem -> new OrderItemDto(
                                        orderItem.getItem().getName(),
                                        orderItem.getOrderPrice(),
                                        orderItem.getCount()
                                ))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
        return JsonResult.OK(collect);
    }

    /**
     * 주문 단건 조회
     */
    @GetMapping(value = "/{id}")
    public JsonResult findOrder(@PathVariable("id") Long id) {
        Order findOrder = orderService.findOrder(id);
        OrderDto order = new OrderDto(
                findOrder.getId(),
                findOrder.getOrderDate(),
                OrderStatus.ORDER.equals(findOrder.getStatus()) ? "주문" : "주문취소",
                findOrder.getDelivery().getAddress().fullAddress(),
                findOrder.getOrderItems().stream()
                        .map(orderItem -> new OrderItemDto(
                                orderItem.getItem().getName(),
                                orderItem.getOrderPrice(),
                                orderItem.getCount()
                        ))
                        .collect(Collectors.toList())
        );
        return JsonResult.OK(order);
    }

    /**
     * 주문 저장
     */
    @PostMapping
    public JsonResult saveOrder(@RequestBody @Validated CreateOrderRequest request, BindingResult bindingResult) {

        //TODO validation check
        if (request.getItemInfos().size() > 0) {
            for (ItemInfo itemInfo : request.getItemInfos()) {
                if (itemInfo.getItemId() == null) {
                    bindingResult.reject("NotEmpty.itemId", "itemId는 필수값입니다.");
                }
                if (itemInfo.getOrderCount() == 0) {
                    bindingResult.reject("NotEmpty.orderCount", "orderCount는 필수값입니다.");
                }
                if (itemInfo.getItemId() != null) {
                    if (itemInfo.getItemId() < 1) {
                        bindingResult.reject("Positive.itemId", "itemId는 0 이상이어야 합니다.");
                    }
                }
                if (itemInfo.getOrderCount() < 1) {
                    bindingResult.reject("Positive.orderCount", "orderCount는 0 이상이어야 합니다.");
                }
            }
        }

        if (bindingResult.hasErrors()) {
            return JsonResult.ERROR(bindingResult.getAllErrors());
        }

        Long savedId = orderService.saveOrderTest(request.getMemberId(), request.getItemInfos());

        return JsonResult.OK(new CreateOrderResponse(savedId));
    }

    /**
     * 주문 수정
     */
    @PutMapping(value = "/{id}")
    public JsonResult updateOrder(@PathVariable("id") Long id,
                                  @RequestBody @Validated UpdateOrderRequest request, BindingResult bindingResult) {
        return JsonResult.OK();
    }

    /**
     * 주문 삭제
     */
    @DeleteMapping(value = "/{id}")
    public JsonResult deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrder(id);
        return JsonResult.OK();
    }

}
