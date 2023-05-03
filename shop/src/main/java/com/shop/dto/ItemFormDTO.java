package com.shop.dto;

import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

// form으로 사용할 DTO는 @NotBlank, @NotEmpty 등 validation 어노테이션을 사용하여 들어오는 값을 검증해주자

@Data
public class ItemFormDTO {
    private Long id;
    
    @NotBlank(message = "상품명은 필수 입력값입니다")
    private String itemNm;

    @NotNull(message = "가격은 필수 입력값입니다")
    private Integer price;

    @NotBlank(message = "상품 설명은 필수 입력값입니다")
    private String itemDetail;

    @NotNull(message = "재고는 필수 입력값입니다")
    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    private List<ItemImgDTO> itemImgDTOList = new ArrayList<>(); // 상품 저장 후 수정할 때 상품 이미지 정보를 저장하는 리스트

    private List<Long> itemImgIds = new ArrayList<>(); // 상품의 이미지 아이디를 저장하는 리스트. 상품 등록 시에는 아직 상품의 이미지를 저장하지 않았기에 아무 값도 들어가 있지 않고 수정 시에 이미지 아이디를 담아 둘 용도로 사용한다.

    // ModelMapper 객체를 이용하여 엔티티 <-> DTO 간 변환 메소드
    private static ModelMapper modelMapper = new ModelMapper(); // 엔티티 - DTO간 변환 객체

    public Item createItem(){ // ItemFormDTO를 엔티티 객체로 반환해주는 메소드
        return modelMapper.map(this, Item.class);
    }

    public static ItemFormDTO of(Item item){ // Item 엔티티를 ItemFormDTO 객체로 반환해주는 메소드
        return modelMapper.map(item, ItemFormDTO.class);
    }
}








