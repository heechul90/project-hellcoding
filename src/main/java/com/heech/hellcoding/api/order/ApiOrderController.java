package com.heech.hellcoding.api.order;

import com.heech.hellcoding.api.order.request.CreateOrderRequest;
import com.heech.hellcoding.core.common.json.JsonResult;
import com.heech.hellcoding.core.shop.order.domain.Order;
import com.heech.hellcoding.core.shop.order.repository.OrderRepository;
import com.heech.hellcoding.core.shop.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        //TODO 변환작업
        return JsonResult.OK(orders);
    }

    @PostMapping
    public JsonResult saveOrder(@RequestBody @Validated CreateOrderRequest request) {
        Long orderedId = orderService.order(request.getMemberId(), request.getItemId(), request.getOrderCount());

        return JsonResult.OK(orderedId);
    }

}
