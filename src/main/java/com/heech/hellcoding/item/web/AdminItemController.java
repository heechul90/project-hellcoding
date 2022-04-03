package com.heech.hellcoding.item.web;

import com.heech.hellcoding.item.domain.Item;
import com.heech.hellcoding.item.service.ItemService;
import com.heech.hellcoding.item.web.form.ItemSaveForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/admin/item")
public class AdminItemController {

    private final ItemService itemService;

    /**
     * 상품 목록
     * @param model
     * @return
     */
    @GetMapping(value = "/list")
    public String itemList(Model model) {
        List<Item> itemList = itemService.findAll();
        model.addAttribute("itemList", itemList);
        return "admin/item/itemList";
    }

    /**
     * 상품 상세
     * @param itemId
     * @param model
     * @return
     */
    @GetMapping(value = "/list/{itemId}")
    public String itemDetail(@PathVariable long itemId, Model model) {
        Optional<Item> item = itemService.findById(itemId);
        model.addAttribute("item", item.get());
        return "admin/item/itemDetail";
    }

    @GetMapping(value = "/add")
    public String addItemForm(Model model) {
        model.addAttribute("item", new ItemSaveForm());
        return "admin/item/addItemForm";
    }

    @PostMapping(value = "/add")
    public String addItem(@Validated @ModelAttribute("item") ItemSaveForm form, BindingResult bindingResult,
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
            return "admin/item/addItemForm";
        }

        Item savedItem = itemService.save(new Item(form.getItemName(), form.getPrice(), form.getQuantity()));
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/admin/item/list/{itemId}";
    }

    @GetMapping(value = "/{itemId}/edit")
    public String editItemForm(Model model) {
        return "admin/item/editItemForm";
    }

    @PostMapping(value = "/{itemId}/edit")
    public String editItem(Model model) {
        return "";
    }

}
