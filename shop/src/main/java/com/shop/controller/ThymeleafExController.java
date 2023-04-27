package com.shop.controller;

import com.shop.dto.ItemDTO;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/thymeleaf/*")
public class ThymeleafExController {

    @GetMapping(value = "/ex01")
    public String thymeleafExample01(Model model){
        model.addAttribute("data", "타임리프 예제입니다.");

        return "thymeleafEx/thymeleafEx01"; // ViewResolver로 가서 앞뒤에 prefix, suffix 붙여준다
        // resources/templates 기준으로 뒤에 .html을 붙여서 찾아간다
    }

    @GetMapping(value = "/ex02")
    public String thymeleafExample02(Model model){
        // 지금은 DTO 객체에 내가 임의의 값을 넣었지만,
        // 실제로는 엔티티에서 값을 가져와서 여기에 넣어주면 될 것이다 (PK로 보통 읽어올 것이니까)

        ItemDTO itemDTO = new ItemDTO();

        itemDTO.setItemNm("테스트 상품1");
        itemDTO.setItemDetail("상품 상세 설명");
        itemDTO.setPrice(10000);
        itemDTO.setRegTime(LocalDateTime.now());

        model.addAttribute("itemDTO", itemDTO);

        return "thymeleafEx/thymeleafEx02";
    }

    @GetMapping(value = "/ex03")
    public String thymeleafExample03(Model model){
        List<ItemDTO> itemDTOList = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            ItemDTO itemDTO = new ItemDTO();

            itemDTO.setItemNm("테스트 상품" + i);
            itemDTO.setItemDetail("상품 상세 설명" + i);
            itemDTO.setPrice(10000);
            itemDTO.setRegTime(LocalDateTime.now());

            itemDTOList.add(itemDTO);
        }

        model.addAttribute("itemDTOList", itemDTOList);

        return "thymeleafEx/thymeleafEx03";
    }

    @GetMapping(value = "/ex04")
    public String thymeleafExample04(Model model){
        List<ItemDTO> itemDTOList = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            ItemDTO itemDTO = new ItemDTO();

            itemDTO.setItemNm("테스트 상품" + i);
            itemDTO.setItemDetail("상품 상세 설명" + i);
            itemDTO.setPrice(1000 * i);
            itemDTO.setRegTime(LocalDateTime.now());

            itemDTOList.add(itemDTO);
        }

        model.addAttribute("itemDTOList", itemDTOList);

        return "thymeleafEx/thymeleafEx04";
    }

    @GetMapping(value = "/ex05")
    public String thymeleafExample05(){
        return "thymeleafEx/thymeleafEx05";
    }

    @GetMapping(value = "/ex06")
    public String thymeleafExample06(String param1, String param2, Model model){
        model.addAttribute("param1", param1);
        model.addAttribute("param2", param2);

        return "thymeleafEx/thymeleafEx06";
    }

    @GetMapping(value = "/ex07")
    public String thymeleafExample07(){
        return "thymeleafEx/thymeleafEx07";
    }
}









