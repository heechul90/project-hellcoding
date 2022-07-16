package com.heech.hellcoding.core.shop.delivery.repository;

import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import com.heech.hellcoding.core.shop.delivery.domain.Delivery;
import com.heech.hellcoding.core.shop.delivery.domain.DeliveryStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DeliveryRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    DeliveryRepository deliveryRepository;

    private Delivery getDelivery(String zipcode, String address, String detailAddress) {
        return Delivery.createDeliveryBuilder()
                .address(new Address(zipcode, address, detailAddress))
                .build();
    }

    @Test
    public void saveTest() {
        //given
        Delivery delivery = getDelivery("12345", "seoul", "gangnam-ro");

        //when
        Delivery savedDelivery = deliveryRepository.save(delivery);

        //then
        Delivery findDelivery = deliveryRepository.findById(savedDelivery.getId())
                .orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));
        assertThat(findDelivery.getStatus()).isEqualTo(DeliveryStatus.READY);
    }

    @Test
    public void updateTest() {
        //given
        Delivery delivery = getDelivery("12345", "seoul", "gangnam-ro");
        em.persist(delivery);
        em.flush();
        em.clear();

        //when
        Delivery findDelivery = deliveryRepository.findById(delivery.getId())
                .orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));
        findDelivery.delivery();
        Address addressParam = new Address("11111", "sejong", "hanuri");
        findDelivery.updateDeliveryBuilder()
                .address(addressParam)
                .build();
        em.flush();
        em.clear();

        //then
        Delivery updatedDelivery = deliveryRepository.findById(delivery.getId())
                .orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));
        assertThat(updatedDelivery.getStatus()).isEqualTo(DeliveryStatus.DELIVERY);
        assertThat(updatedDelivery.getAddress().getZipcode()).isEqualTo("11111");
        assertThat(updatedDelivery.getAddress().getAddress()).isEqualTo("sejong");
        assertThat(updatedDelivery.getAddress().getDetailAddress()).isEqualTo("hanuri");
    }

    @Test
    public void deleteTest() {
        //given
        Delivery delivery = getDelivery("12345", "seoul", "gangnam-ro");
        em.persist(delivery);
        em.flush();
        em.clear();

        //when
        Delivery findDelivery = deliveryRepository.findById(delivery.getId())
                .orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));
        deliveryRepository.delete(findDelivery);

        //then
        assertThatThrownBy(() -> deliveryRepository.findById(delivery.getId())
                .orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다.")))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("조회에");
    }

}