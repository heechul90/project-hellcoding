package com.heech.hellcoding.item.web;

import com.heech.hellcoding.item.domain.Item;
import com.heech.hellcoding.item.service.ItemService;
import com.heech.hellcoding.item.web.form.AddItemForm;
import com.heech.hellcoding.item.web.form.EditItemForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/front/item")
public class FrontItemController {

    private final ItemService itemService;

    /**
     * 상품 목록
     */
    @GetMapping(value = "/list")
    public String itemList(Model model) {
        List<Item> itemList = itemService.findAll();
        model.addAttribute("itemList", itemList);
        return "front/item/itemList";
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

        Item savedItem = itemService.save(new Item(form.getItemName(), form.getPrice(), form.getQuantity()));
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/front/item/{itemId}";
    }

    /**
     * Item 수정 폼
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

        itemService.update(itemId, new Item(form.getItemName(), form.getPrice(), form.getQuantity()));
        return "redirect:/front/item/{itemId}";
    }

}
