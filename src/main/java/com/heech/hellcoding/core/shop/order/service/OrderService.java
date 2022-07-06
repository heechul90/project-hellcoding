package com.heech.hellcoding.core.shop.order.service;

import com.heech.hellcoding.api.shop.order.request.ItemInfo;
import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.repository.MemberRepository;
import com.heech.hellcoding.core.shop.delivery.domain.Delivery;
import com.heech.hellcoding.core.shop.delivery.domain.DeliveryStatus;
import com.heech.hellcoding.core.shop.item.info.domain.Item;
import com.heech.hellcoding.core.shop.item.info.repository.ItemRepository;
import com.heech.hellcoding.core.shop.order.domain.Order;
import com.heech.hellcoding.core.shop.order.dto.OrderSearchCondition;
import com.heech.hellcoding.core.shop.order.repository.OrderQueryRepository;
import com.heech.hellcoding.core.shop.order.repository.OrderRepository;
import com.heech.hellcoding.core.shop.orderItem.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문 목록 조회
     */
    public Page<Order> findOrders(OrderSearchCondition condition, Pageable pageable) {
        return orderQueryRepository.findOrders(condition, pageable);
    }

    /**
     * 주문 단건 조회
     */
    public Order findOrder(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));
    }

    /**
     * 주문 저장(주문하기)
     */
    @Transactional
    public Long saveOrder(Long memberId, List<ItemInfo> itemInfos) {
        //회원 조회
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));

        //배송정보 생성
        Delivery delivery = new Delivery(findMember.getAddress());

        //상품 조회
        List<Long> itemIds = itemInfos.stream().map(info -> info.getItemId())
                .collect(Collectors.toList());
        List<Item> items = itemRepository.findByIdIn(itemIds);

        List<OrderItem> orderItems = new ArrayList<>();
        for (Item item : items) {
            OrderItem orderItem = OrderItem.createOrderItemBuilder()
                    .item(item)
                    .orderPrice(item.getPrice())
                    .count(itemInfos.stream().filter(i -> i.getItemId().equals(item.getId())).findAny().orElseThrow(() -> new NoSuchElementException("잘못된 접근")).getOrderCount())
                    .build();
            orderItems.add(orderItem);
        }

        Order order = new Order(findMember, delivery, orderItems);

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long id) {
        Order findOrder = orderRepository.findById(id).orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));
        findOrder.cancel();
    }

    /**
     * 주문 삭제
     */
    @Transactional
    public void deleteOrder(Long id) {
        Order findOrder = orderRepository.findById(id).orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));
        orderRepository.delete(findOrder);
    }

}
