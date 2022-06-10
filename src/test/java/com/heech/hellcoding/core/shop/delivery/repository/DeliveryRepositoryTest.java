package com.heech.hellcoding.core.shop.delivery.repository;

import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import com.heech.hellcoding.core.shop.delivery.domain.Delivery;
import com.heech.hellcoding.core.shop.delivery.domain.DeliveryStatus;
import com.heech.hellcoding.core.shop.delivery.dto.DeliverySearchCondition;
import org.assertj.core.api.Assertions;
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
    public void findDeliveriesTest() throws Exception{
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
        Page<Delivery> content1 = deliveryRepository.findDeliveries(condition, pageRequest);

        //then
        assertThat(content1.getTotalElements()).isEqualTo(50);
        assertThat(content1.getContent().size()).isEqualTo(10);

        //when
        condition.setSearchDeliveryStatus(DeliveryStatus.DELIVERY);
        Page<Delivery> content2 = deliveryRepository.findDeliveries(condition, pageRequest);

        //then
        assertThat(content2.getTotalElements()).isEqualTo(0);
        assertThat(content2.getContent().size()).isEqualTo(0);
    }

    @Test
    public void saveTest() throws Exception{
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
    public void updateTest() throws Exception{
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
    public void deleteTest() throws Exception{
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