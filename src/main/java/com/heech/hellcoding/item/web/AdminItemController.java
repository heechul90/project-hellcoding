package com.heech.hellcoding.item.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/admin/item")
public class AdminItemController {

    @GetMapping()
    public String item(Model model) {
        return "admin/item/item";
    }
}
