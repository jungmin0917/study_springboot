package com.shop.controller;

import com.shop.dto.ItemFormDTO;
import com.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping(value = "/admin/item/new")
    public String itemForm(Model model){
        model.addAttribute("itemFormDTO", new ItemFormDTO()); // 빈 DTO 객체 전달해서 뷰에서 자동으로 폼 관련 속성 추가하도록 함

        return "/item/itemForm";
    }

    @PostMapping(value = "/admin/item/new")
    public String itemNew(@Valid ItemFormDTO itemFormDTO, BindingResult bindingResult, Model model, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList){

        if(bindingResult.hasErrors()){
            return "item/itemForm";
        }

        // itemFormDTO.getId가 null 이거는 왜 하는 거지..?
        // 첫 번째 이미지가 없다면 에러메시지와 함께 상품 등록 페이지로 전환함.
        if(itemImgFileList.get(0).isEmpty() && itemFormDTO.getId() == null){
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력값입니다");
            return "item/itemForm";
        }

        try{
            itemService.saveItem(itemFormDTO, itemImgFileList); // 상품 저장 로직을 호출함. 매개변수로 상품 정보와 상품 이미지 정보를 담고 있는 itemImgFileList를 넘겨줌.
        }catch (Exception $e){
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다");
            return "item/itemForm";
        }

        return "redirect:/"; // 정상적으로 등록되었다면 메인 페이지로 이동
    }

}










