package com.heech.hellcoding.core.shop.delivery.repository;

import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.shop.ShopTestConfig;
import com.heech.hellcoding.core.shop.delivery.domain.Delivery;
import com.heech.hellcoding.core.shop.delivery.domain.DeliveryStatus;
import com.heech.hellcoding.core.shop.delivery.dto.DeliverySearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(ShopTestConfig.class)
class DeliveryQueryRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    DeliveryQueryRepository deliveryQueryRepository;

    private Delivery getDelivery(String zipcode, String address, String detailAddress) {
        return Delivery.createDeliveryBuilder()
                .address(new Address(zipcode, address, detailAddress))
                .build();
    }

    @Test
    void findDeliveriesTest() {
        //given
        for (int i = 0; i < 50; i++) {
            Delivery delivery = getDelivery(String.format("%05d", i), "seoul" + i, "gangnam-ro" + i);
            em.persist(delivery);
        }
        em.flush();
        em.clear();

        //when
        DeliverySearchCondition condition = new DeliverySearchCondition();
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Delivery> content1 = deliveryQueryRepository.findDeliveries(condition, pageRequest);

        //then
        assertThat(content1.getTotalElements()).isEqualTo(50);
        assertThat(content1.getContent().size()).isEqualTo(10);

        //when
        condition.setSearchDeliveryStatus(DeliveryStatus.DELIVERY);
        Page<Delivery> content2 = deliveryQueryRepository.findDeliveries(condition, pageRequest);

        //then
        assertThat(content2.getTotalElements()).isEqualTo(0);
        assertThat(content2.getContent().size()).isEqualTo(0);
    }
}