package com.shop.controller;

import com.shop.dto.ItemFormDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemController {

    @GetMapping(value = "/admin/item/new")
    public String itemForm(Model model){
        model.addAttribute("itemFormDTO", new ItemFormDTO()); // 빈 DTO 객체 전달해서 뷰에서 자동으로 폼 관련 속성 추가하도록 함

        return "/item/itemForm";
    }

}
