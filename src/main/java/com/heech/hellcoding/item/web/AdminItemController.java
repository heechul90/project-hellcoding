package com.heech.hellcoding.item.web;

import com.heech.hellcoding.item.domain.Item;
import com.heech.hellcoding.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/admin/item")
public class AdminItemController {

    private final ItemService itemService;

    @GetMapping(value = "/list")
    public String item(Model model) {
        List<Item> itemList = itemService.findAll();
        model.addAttribute("itemList", itemList);
        return "admin/item/itemList";
    }

    @GetMapping(value = "/list/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Optional<Item> item = itemService.findById(itemId);
        model.addAttribute("item", item);
        return "admin/item/itemDetail";
    }

}
