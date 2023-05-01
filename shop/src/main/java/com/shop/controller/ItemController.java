package com.shop.controller;

import com.shop.dto.ItemFormDTO;
import com.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
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

    @GetMapping(value = "/admin/item/{itemId}")
    public String itemDtl(@PathVariable("itemId") Long itemId, Model model){

        try {
            ItemFormDTO itemFormDTO = itemService.getItemDtl(itemId); // 요청받은 itemId로 itemFormDTO를 만들어 반환함
            model.addAttribute("itemFormDTO", itemFormDTO);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "존재하지 않는 상품입니다");
            model.addAttribute("itemFormDTO", new ItemFormDTO()); // 빈 itemFormDTO 객체 생성함
            return "item/itemForm"; // 상품 등록 페이지로 감 (사실 상품 등록 페이지로 간다기 보단, 해당 view에서 itemFormDTO 내부 값이 있냐 없냐에 따라 등록인지 수정인지로 갈려서 그럼)
        }

        return "item/itemForm";
    }

    @PostMapping(value = "/admin/item/{itemId}")
    public String itemUpdate(@Valid ItemFormDTO itemFormDTO, BindingResult bindingResult, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList, Model model){
        if(bindingResult.hasErrors()){
            return "item/itemForm";
        }

        if(itemImgFileList.get(0).isEmpty() && itemFormDTO.getId() == null){
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력값입니다");
            return "/item/itemForm";
        }

        try {
            itemService.updateItem(itemFormDTO, itemImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다");
            return "/item/itemForm";
        }
        
        return "redirect:/"; // 수정 성공 시 메인 페이지로
    }
}











