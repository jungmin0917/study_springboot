package com.shop.controller;

import com.shop.dto.ItemSearchDTO;
import com.shop.dto.MainItemDTO;
import com.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ItemService itemService;

    @GetMapping(value = "/")
    // Optional 래퍼 클래스를 쓰는 것은 null-safe한 코딩을 하기 위함임.
    public String main(ItemSearchDTO itemSearchDTO, Optional<Integer> page, Model model){

        // page.isPresent()는, Optional객체의 클래스 중 하나로 해당 객체가 null인지 아닌지를 파악함. 여기서는 해당 page가 null이면 0을 쓰고, 아니면 get() 메소드로 해당 옵셔널 객체의 값을 가져옴
        // PageRequest.of(현재 페이지, 페이지 당 레코드 수)를 이용하여 Pageable 객체를 생성함
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);

        // 해당 메소드를 이용하여 페이징 처리된 Page 객체를 반환받는다.
        Page<MainItemDTO> items = itemService.getMainItemPage(itemSearchDTO, pageable);

        model.addAttribute("items", items);
        model.addAttribute("itemSearchDTO", itemSearchDTO); // 검색했던 조건이 남아있도록 다시 반환
        model.addAttribute("maxPage", 5); // 이건 해당 페이지에서 최대로 이동할 수 있는 페이지 같음

        return "main";
    }

}
