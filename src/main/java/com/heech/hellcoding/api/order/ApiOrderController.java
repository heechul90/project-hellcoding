package com.heech.hellcoding.api.order;

import com.heech.hellcoding.api.order.request.CreateOrderRequest;
import com.heech.hellcoding.core.common.json.JsonResult;
import com.heech.hellcoding.core.shop.order.domain.Order;
import com.heech.hellcoding.core.shop.order.domain.OrderStatus;
import com.heech.hellcoding.core.shop.order.dto.OrderDto;
import com.heech.hellcoding.core.shop.order.repository.OrderRepository;
import com.heech.hellcoding.core.shop.order.service.OrderService;
import com.heech.hellcoding.core.shop.orderItem.dto.OrderItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/orders")
public class ApiOrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    /**
     * 주문 조회(목록)
     */
    @GetMapping
    public JsonResult orders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDto> collect = orders.stream()
                .map(order -> new OrderDto(
                        order.getId(),
                        order.getOrderDate(),
                        order.getStatus(),
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

    /*@PostMapping
    public JsonResult saveOrder(@RequestBody @Validated CreateOrderRequest request) {
        Long orderId = orderService.order(request.getMemberId(), request.getItemId(), request.getOrderCount());

        return JsonResult.OK(orderId);
    }*/

}
