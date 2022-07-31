package com.heech.hellcoding.api.shop.delivery;

import com.heech.hellcoding.api.shop.delivery.request.UpdateDeliveryRequest;
import com.heech.hellcoding.api.shop.delivery.response.UpdateDeliveryResponse;
import com.heech.hellcoding.core.common.entity.Address;
import com.heech.hellcoding.core.common.json.JsonResult;
import com.heech.hellcoding.core.shop.delivery.domain.Delivery;
import com.heech.hellcoding.core.shop.delivery.domain.DeliveryStatus;
import com.heech.hellcoding.core.shop.delivery.dto.DeliveryDto;
import com.heech.hellcoding.core.shop.delivery.dto.DeliverySearchCondition;
import com.heech.hellcoding.core.shop.delivery.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/shop/deliveries")
public class ApiDeliveryController {

    private final DeliveryService deliveryService;

    /**
     * 배송 목록 조회
     */
    @GetMapping
    public JsonResult findDeliveries(DeliverySearchCondition condition, Pageable pageable) {
        Page<Delivery> content = deliveryService.findDeliveries(condition, pageable);
        List<DeliveryDto> collect = content.getContent().stream()
                .map(delivery -> new DeliveryDto(
                        delivery.getId(),
                        DeliveryStatus.DELIVERY.equals(delivery.getStatus()) ? "배달중" : DeliveryStatus.COMPLETE.equals(delivery.getStatus()) ? "배달완료" : "배달준비중",
                        delivery.getAddress().fullAddress()
                ))
                .collect(Collectors.toList());
        return JsonResult.OK(collect);
    }

    /**
     * 배송 단건 조회
     */
    @GetMapping(value = "/{deliveryId}")
    public JsonResult findDelivery(@PathVariable("deliveryId") Long deliveryId) {
        Delivery findDelivery = deliveryService.findDelivery(deliveryId);
        DeliveryDto delivery = new DeliveryDto(
                findDelivery.getId(),
                DeliveryStatus.DELIVERY.equals(findDelivery.getStatus()) ? "배달중" : DeliveryStatus.COMPLETE.equals(findDelivery.getStatus()) ? "배달완료" : "배달준비중",
                findDelivery.getAddress().fullAddress()
        );
        return JsonResult.OK(delivery);
    }

    /**
     * 배송 수정
     */
    @PutMapping(value = "/{deliveryId}")
    public JsonResult updateDelivery(@PathVariable("deliveryId") Long deliveryId,
                                     @Validated @RequestBody UpdateDeliveryRequest request, BindingResult bindingResult) {

        /*if (bindingResult.hasErrors()) {
            log.info("bindingResult = {}", bindingResult);
            return JsonResult.ERROR(bindingResult.getAllErrors());
        }*/

        deliveryService.updateDelivery(deliveryId, new Address(request.getZipcode(), request.getAddress(), request.getDetailAddress()));
        Delivery findDelivery = deliveryService.findDelivery(deliveryId);

        return JsonResult.OK(new UpdateDeliveryResponse(findDelivery.getId()));
    }

    /**
     * 배송 배달시작
     */
    @PutMapping(value = "/{deliveryId}/delivery")
    public JsonResult deliveryDelivery(@PathVariable("deliveryId") Long deliveryId) {
        deliveryService.deliveryDelivery(deliveryId);
        return JsonResult.OK();
    }

    /**
     * 배송 배달완료
     */
    @PutMapping(value = "/{deliveryId}/complete")
    public JsonResult completeDelivery(@PathVariable("deliveryId") Long deliveryId) {
        deliveryService.completeDelivery(deliveryId);
        return JsonResult.OK();
    }
}
