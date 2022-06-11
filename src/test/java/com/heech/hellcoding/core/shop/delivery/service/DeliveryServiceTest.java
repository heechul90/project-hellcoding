package com.heech.hellcoding.core.shop.delivery.service;

import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.shop.delivery.domain.Delivery;
import com.heech.hellcoding.core.shop.delivery.domain.DeliveryStatus;
import com.heech.hellcoding.core.shop.delivery.dto.DeliverySearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class DeliveryServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    DeliveryService deliveryService;

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
        Page<Delivery> content = deliveryService.findDeliveries(condition, pageRequest);

        //then
        assertThat(content.getTotalElements()).isEqualTo(50);
        assertThat(content.getContent().size()).isEqualTo(10);
    }

    @Test
    void findDeliveryTest() {
        //given
        Delivery delivery = getDelivery("11111", "seoul", "dangun-ro");
        em.persist(delivery);

        //when
        Delivery findDelivery = deliveryService.findDelivery(delivery.getId());

        //then
        assertThat(findDelivery.getStatus()).isEqualTo(DeliveryStatus.READY);
        assertThat(findDelivery.getAddress().getZipcode()).isEqualTo("11111");
        assertThat(findDelivery.getAddress().getAddress()).isEqualTo("seoul");
        assertThat(findDelivery.getAddress().getDetailAddress()).isEqualTo("dangun-ro");
    }

    @Test
    void saveDeliveryTest() {
        //given
        Delivery delivery = getDelivery("11111", "seoul", "dangun-ro");

        //when
        Long savedDeliveryId = deliveryService.saveDelivery(delivery);

        //then
        Delivery findDelivery = deliveryService.findDelivery(savedDeliveryId);
        assertThat(findDelivery.getStatus()).isEqualTo(DeliveryStatus.READY);
        assertThat(findDelivery.getAddress().getZipcode()).isEqualTo("11111");
        assertThat(findDelivery.getAddress().getAddress()).isEqualTo("seoul");
        assertThat(findDelivery.getAddress().getDetailAddress()).isEqualTo("dangun-ro");
    }

    @Test
    void updateDeliveryTest() {
        //given
        Delivery delivery = getDelivery("11111", "seoul", "dangun-ro");
        em.persist(delivery);
        em.flush();
        em.clear();

        //when
        deliveryService.updateDelivery(delivery.getId(), new Address("22222", "jeju", "hae-ro"));
        em.flush();
        em.clear();

        //then
        Delivery findDelivery = deliveryService.findDelivery(delivery.getId());
        assertThat(findDelivery.getStatus()).isEqualTo(DeliveryStatus.READY);
        assertThat(findDelivery.getAddress().getZipcode()).isEqualTo("22222");
        assertThat(findDelivery.getAddress().getAddress()).isEqualTo("jeju");
        assertThat(findDelivery.getAddress().getDetailAddress()).isEqualTo("hae-ro");
    }

    @Test
    void deliveryDeliveryTest() {
        //given
        Delivery delivery = getDelivery("11111", "seoul", "dangun-ro");
        em.persist(delivery);
        em.flush();
        em.clear();

        //when
        deliveryService.deliveryDelivery(delivery.getId());
        em.flush();
        em.clear();

        //then
        Delivery findDelivery = deliveryService.findDelivery(delivery.getId());
        assertThat(findDelivery.getStatus()).isEqualTo(DeliveryStatus.DELIVERY);
    }

    @Test
    void completeDeliveryTest() {
        //given
        Delivery delivery = getDelivery("11111", "seoul", "dangun-ro");
        em.persist(delivery);
        em.flush();
        em.clear();

        //when
        deliveryService.completeDelivery(delivery.getId());
        em.flush();
        em.clear();

        //then
        Delivery findDelivery = deliveryService.findDelivery(delivery.getId());
        assertThat(findDelivery.getStatus()).isEqualTo(DeliveryStatus.COMPLETE);
    }
}