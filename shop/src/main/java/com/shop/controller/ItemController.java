package com.shop.controller;

import com.shop.dto.ItemFormDTO;
import com.shop.dto.ItemSearchDTO;
import com.shop.entity.Item;
import com.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.Optional;

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

    @GetMapping(value = {"/admin/items", "/admin/items/{page}"}) // 페이지 번호가 있는 경우와 없는 경우 두 가지로 매핑함
    public String itemManage(ItemSearchDTO itemSearchDTO, @PathVariable("page") Optional<Integer> page, Model model){
        // 페이징을 위해서 PageRequest.of 메소드를 통해 Pageable 객체를 생성해준다. 첫 번째 파라미터로는 조회할 페이지 번호, 두 번째 파라미터로는 한 번에 가지고 올 데이터 수를 입력한다. URL 경로에 페이지 번호가 있으면 해당 페이지를 조회하게 하고, 없으면 0페이지를 조회하도록 한다.
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);

        Page<Item> items = itemService.getAdminItemPage(itemSearchDTO, pageable); // Pageable 객체와 검색 정보 DTO를 넘겨서 Item을 조회한다. (Page<item> 형태로 반환)
        model.addAttribute("items", items); // 조회한 상품 데이터 및 페이징 정보를 뷰에 전달함
        model.addAttribute("itemSearchDTO", itemSearchDTO); // 페이지 전환 시 기존 검색 조건을 유지한 채 이동하기 위해 뷰에 다시 검색조건 DTO를 전달함
        model.addAttribute("maxPage", 5); // 상품 관리 메뉴 하단에 보여줄 페이지 번호의 최대 개수. 5로 설정했으므로 최대 5개의 이동할 페이지 번호만 보여줌.

        return "item/itemMng";
    }
}











