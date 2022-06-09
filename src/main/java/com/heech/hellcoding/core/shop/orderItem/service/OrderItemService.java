package com.heech.hellcoding.core.shop.orderItem.service;

import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import com.heech.hellcoding.core.shop.item.info.domain.Item;
import com.heech.hellcoding.core.shop.item.info.repository.ItemRepository;
import com.heech.hellcoding.core.shop.order.domain.Order;
import com.heech.hellcoding.core.shop.order.repository.OrderRepository;
import com.heech.hellcoding.core.shop.orderItem.domain.OrderItem;
import com.heech.hellcoding.core.shop.orderItem.dto.OrderItemSearchCondition;
import com.heech.hellcoding.core.shop.orderItem.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문상품 목록 조회
     */
    public Page<OrderItem> findOrderItems(OrderItemSearchCondition condition, Pageable pageable) {
        return orderItemRepository.findOrderItems(condition, pageable);
    }

    /**
     * 주문상품 단건 조회
     */
    public OrderItem findOrderItem(Long orderitemId) {
        return orderItemRepository.findById(orderitemId).orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));
    }

    /**
     * 주문상품 추가
     */
    @Transactional
    public Long saveOrderItem(Long orderId, Long itemId, int count) {
        Order findOrder = orderRepository.findById(orderId).orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));
        Item findItem = itemRepository.findById(itemId).orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));
        OrderItem orderItem = new OrderItem(findOrder, findItem, count);
        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
        return savedOrderItem.getId();
    }

    /**
     * 주문상품 수정
     */
    @Transactional
    public void updateOrderItem(Long orderItemId, Integer orderPrice, Integer count) {
        OrderItem findOrderItem = orderItemRepository.findById(orderItemId).orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));
        findOrderItem.updateOrderitemBuilder()
                .orderPrice(orderPrice)
                .count(count)
                .build();
    }

    /**
     * 주문상품 삭제
     */
    @Transactional
    public void deleteOrderItem(Long orderItemId) {
        OrderItem findOrderItem = orderItemRepository.findById(orderItemId).orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));
        orderItemRepository.delete(findOrderItem);
    }

}
