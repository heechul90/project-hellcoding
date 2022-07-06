package com.heech.hellcoding.core.shop.order.repository;

import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.member.domain.Member;
import com.heech.hellcoding.core.member.repository.MemberRepository;
import com.heech.hellcoding.core.shop.delivery.domain.Delivery;
import com.heech.hellcoding.core.shop.delivery.domain.DeliveryStatus;
import com.heech.hellcoding.core.shop.item.book.domain.Book;
import com.heech.hellcoding.core.shop.order.domain.Order;
import com.heech.hellcoding.core.shop.order.dto.OrderSearchCondition;
import com.heech.hellcoding.core.shop.orderItem.domain.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderRepositoryTest {

    @PersistenceContext EntityManager em;

    @Autowired OrderRepository orderRepository;

    @Test
    public void saveTest() throws Exception{
        //given

        //when

        //then
    }

}