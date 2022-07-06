package com.heech.hellcoding.core.shop.delivery.service;

import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import com.heech.hellcoding.core.shop.delivery.domain.Delivery;
import com.heech.hellcoding.core.shop.delivery.dto.DeliverySearchCondition;
import com.heech.hellcoding.core.shop.delivery.repository.DeliveryQueryRepository;
import com.heech.hellcoding.core.shop.delivery.repository.DeliveryRepository;
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
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryQueryRepository deliveryQueryRepository;

    /**
     * 배송 목록 조회
     */
    public Page<Delivery> findDeliveries(DeliverySearchCondition condition, Pageable pageable) {
        return deliveryQueryRepository.findDeliveries(condition, pageable);
    }

    /**
     * 배송 단건 조회
     */
    public Delivery findDelivery(Long deliveryId) {
        return deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));
    }

    /**
     * 배송 저장
     */
    @Transactional
    public Long saveDelivery(Delivery delivery) {
        Delivery savedDelivery = deliveryRepository.save(delivery);
        return savedDelivery.getId();
    }

    /**
     * 배송 수정
     */
    @Transactional
    public void updateDelivery(Long deliveryId, Address updateAddressParam) {
        Delivery findDelivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));
        findDelivery.updateDeliveryBuilder()
                .address(updateAddressParam)
                .build();
    }

    /**
     * 배송 배달시작
     */
    @Transactional
    public void deliveryDelivery(Long deliveryId) {
        Delivery findDelivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));
        findDelivery.delivery();
    }

    /**
     * 배송 배달완료
     */
    @Transactional
    public void completeDelivery(Long deliveryId) {
        Delivery findDelivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));
        findDelivery.complete();
    }

}
