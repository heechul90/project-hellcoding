package com.heech.hellcoding.api.order;

import com.heech.hellcoding.api.order.request.CreateOrderRequest;
import com.heech.hellcoding.api.order.request.ItemInfo;
import com.heech.hellcoding.api.order.response.CreateOrderResponse;
import com.heech.hellcoding.core.common.json.JsonResult;
import com.heech.hellcoding.core.shop.order.domain.Order;
import com.heech.hellcoding.core.shop.order.domain.OrderStatus;
import com.heech.hellcoding.core.shop.order.dto.OrderDto;
import com.heech.hellcoding.core.shop.order.repository.OrderRepository;
import com.heech.hellcoding.core.shop.order.service.OrderService;
import com.heech.hellcoding.core.shop.orderItem.dto.OrderItemDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/orders")
public class ApiOrderController {

    private final OrderService orderService;


    //TODO 주문 목록 조회

    //TODO 주문 단건 조회

    /**
     * 주문 저장
     */
    @PostMapping
    public JsonResult saveOrder(@RequestBody @Validated CreateOrderRequest request, BindingResult bindingResult) {

        //TODO validation check
        if (bindingResult.hasErrors()) {
            return JsonResult.ERROR(bindingResult.getAllErrors());
        }

        saveItemDto saveItemDto = new saveItemDto();
        saveItemDto.setMemberId(1L);

        ItemInfo itemInfo1 = new ItemInfo();
        itemInfo1.setItemId(1L);
        itemInfo1.setOrderCount(10);

        ItemInfo itemInfo2 = new ItemInfo();
        itemInfo2.setItemId(2L);
        itemInfo2.setOrderCount(15);

        saveItemDto.getItemInfos().add(itemInfo1);
        saveItemDto.getItemInfos().add(itemInfo2);

        orderService.saveOrderTest(saveItemDto.getMemberId(), saveItemDto.getItemInfos());

        return JsonResult.OK(new CreateOrderResponse());
    }

    @Data
    static class saveItemDto {
        private Long memberId;
        private List<ItemInfo> itemInfos;
    }


    //TODO 주문 수정

    //TODO 주문 삭제

}
