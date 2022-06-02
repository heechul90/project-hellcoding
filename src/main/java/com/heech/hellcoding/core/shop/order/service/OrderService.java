package com.heech.hellcoding.core.shop.order.service;

import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.repository.MemberRepository;
import com.heech.hellcoding.core.shop.delivery.domain.Delivery;
import com.heech.hellcoding.core.shop.delivery.domain.DeliveryStatus;
import com.heech.hellcoding.core.shop.item.book.domain.Book;
import com.heech.hellcoding.core.shop.item.book.repository.BookRepository;
import com.heech.hellcoding.core.shop.order.domain.Order;
import com.heech.hellcoding.core.shop.order.repository.OrderRepository;
import com.heech.hellcoding.core.shop.orderItem.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;


    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        //엔티티 조회
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));
        Book findBook = bookRepository.findById(itemId).orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));

        //배송정보 생성
        Delivery delivery = new Delivery(findMember.getAddress(), DeliveryStatus.READY);

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem()
                .item(findBook)
                .orderPrice(findBook.getPrice())
                .count(count)
                .build();

        //주문생성
        /*Order order = Order.createBuilder()
                .member(findMember)
                .delivery(delivery)
                .orderItems(orderItem)
                .build();*/
        Order order = new Order(findMember, delivery, orderItem);

        //주문저장
        orderRepository.save(order);

        return order.getId();
    }

}
