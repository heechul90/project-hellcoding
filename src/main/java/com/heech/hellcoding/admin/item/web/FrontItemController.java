package com.heech.hellcoding.admin.item.web;

import com.heech.hellcoding.core.shop.item.domain.Item;
import com.heech.hellcoding.core.temp.domain.ItemType;
import com.heech.hellcoding.core.shop.item.service.ItemService;
import com.heech.hellcoding.admin.item.form.AddItemForm;
import com.heech.hellcoding.admin.item.form.EditItemForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/front/items")
public class FrontItemController {

    private final ItemService itemService;

    /**
     * 지역 목록
     */
    @ModelAttribute(name = "regionList")
    public Map<String, String> regionList() {
        Map<String, String> regionList = new LinkedHashMap<>();
        regionList.put("SEOUL", "서울");
        regionList.put("BUSAN", "부산");
        regionList.put("JEJU", "제주");
        regionList.put("DAEJEON", "대전");
        regionList.put("SEJONG", "세종");
        return regionList;
    }

    /**
     * Item 종류
     */
    @ModelAttribute(name = "itemTypeList")
    public ItemType[] itemTypeList() {
        return ItemType.values();
    }

    /**
     * 배송 속도
     */
    /*@ModelAttribute(name = "deliveryCodeList")
    public List<DeliveryCode> deliveryCodeList() {
        List<DeliveryCode> deliveryCodeList = new ArrayList<>();
        deliveryCodeList.add(new DeliveryCode("FAST", "빠른 배송"));
        deliveryCodeList.add(new DeliveryCode("NORMAL", "일반 배송"));
        deliveryCodeList.add(new DeliveryCode("SLOW", "느린 배송"));
        return deliveryCodeList;
    }*/

    /**
     * 상품 목록
     */
    @GetMapping()
    public String itemList(Model model) {
        List<Item> itemList = itemService.findAll();
        model.addAttribute("itemList", itemList);
        return "front/item/itemList";
    }

    /**
     * Item 등록 폼
     */
    @GetMapping(value = "/add")
    public String addItemForm(Model model) {
        model.addAttribute("item", new AddItemForm());
        return "front/item/addItemForm";
    }

    /**
     * Item 등록
     */
    @PostMapping(value = "/add")
    public String addItem(@Validated @ModelAttribute("item") AddItemForm form, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {

        //특정 필드 예외가 아닌 전체 예외
        if (form.getPrice() != null && form.getQuantity() != null) {
            int resultPrice = form.getPrice() * form.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);
            return "front/item/addItemForm";
        }

        //Item item = new Item(form.getItemName(), form.getPrice(), form.getQuantity());
        //item.setOpen(form.getOpen());
        //item.setRegionList(form.getRegionList());
        //item.setItemType(form.getItemType());
        //item.setDeliveryCode(form.getDeliveryCode());

        //Item savedItem = itemService.save(item);
        //redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/front/items/{itemId}";
    }

    /**
     * 상품 상세
     */
    @GetMapping(value = "/{itemId}")
    public String itemDetail(@PathVariable long itemId, Model model) {
        Optional<Item> item = itemService.findById(itemId);
        model.addAttribute("item", item.get());
        return "front/item/itemDetail";
    }

    /**
     * Item 수정 폼
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/{itemId}/edit")
    public String editItemForm(@PathVariable("itemId") Long itemId, Model model) {
        model.addAttribute("item", itemService.findById(itemId).get());
        return "front/item/editItemForm";
    }

    /**
     * Item 수정
     */
    @PostMapping(value = "/{itemId}/edit")
    public String editItem(@Validated @ModelAttribute("item") EditItemForm form, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           @PathVariable("itemId") Long itemId) {

        //특정 필드 예외가 아닌 전체 예외
        if (form.getPrice() != null && form.getQuantity() != null) {
            int resultPrice = form.getPrice() * form.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);
            return "front/item/editItemForm";
        }

        //Item item = new Item(form.getItemName(), form.getPrice(), form.getQuantity());
        //item.setOpen(form.getOpen());
        //item.setRegionList(form.getRegionList());
        //item.setItemType(form.getItemType());
        //item.setDeliveryCode(form.getDeliveryCode());

        //itemService.update(itemId, item);
        return "redirect:/front/items/{itemId}";
    }

    /**
     * Item 삭제
     */
    @PostMapping(value = "/{itemId}/delete")
    public String deleteItem(@PathVariable("itemId") Long itemId) {
        return "redirect:/front/items";
    }
}
