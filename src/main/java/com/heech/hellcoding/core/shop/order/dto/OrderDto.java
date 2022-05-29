package com.heech.hellcoding.core.shop.order.dto;

import com.heech.hellcoding.core.shop.order.domain.OrderStatus;
import com.heech.hellcoding.core.shop.orderItem.dto.OrderItemDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.tomcat.jni.Address;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class OrderDto {

    private Long orderId;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private String address;
    private List<OrderItemDto> orderItems;

}
