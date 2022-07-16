package com.heech.hellcoding.core.shop.delivery.domain;

import com.heech.hellcoding.core.common.entity.Address;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DeliveryTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void createDeliveryTest() {
        //given
        Delivery delivery = Delivery.createDeliveryBuilder()
                .address(new Address("12345", "seoul", "gangnam-ro"))
                .build();

        //when
        em.persist(delivery);

        //then
        Delivery findDelivery = em.find(Delivery.class, delivery.getId());
        assertThat(findDelivery.getStatus()).isEqualTo(DeliveryStatus.READY);
        assertThat(findDelivery.getAddress().getZipcode()).isEqualTo("12345");
        assertThat(findDelivery.getAddress().getAddress()).isEqualTo("seoul");
        assertThat(findDelivery.getAddress().getDetailAddress()).isEqualTo("gangnam-ro");
    }

    @Test
    public void deliveryTest() {
        //given
        Delivery delivery = Delivery.createDeliveryBuilder()
                .address(new Address("12345", "seoul", "gangnam-ro"))
                .build();
        em.persist(delivery);
        em.flush();
        em.clear();

        //when
        Delivery findDelivery = em.find(Delivery.class, delivery.getId());
        findDelivery.delivery();
        em.flush();
        em.clear();

        //then
        Delivery updatedDelivery = em.find(Delivery.class, delivery.getId());
        assertThat(updatedDelivery.getStatus()).isEqualTo(DeliveryStatus.DELIVERY);
    }

    @Test
    public void completeTest() {
        //given
        Delivery delivery = Delivery.createDeliveryBuilder()
                .address(new Address("12345", "seoul", "gangnam-ro"))
                .build();
        em.persist(delivery);
        em.flush();
        em.clear();

        //when
        Delivery findDelivery = em.find(Delivery.class, delivery.getId());
        findDelivery.complete();
        em.flush();
        em.clear();

        //then
        Delivery updatedDelivery = em.find(Delivery.class, delivery.getId());
        assertThat(updatedDelivery.getStatus()).isEqualTo(DeliveryStatus.COMPLETE);
    }

}