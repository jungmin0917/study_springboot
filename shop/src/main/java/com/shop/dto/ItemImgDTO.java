package com.shop.dto;

import com.shop.entity.ItemImg;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class ItemImgDTO {

    private Long id;
    private String imgName; // 이미지 파일명
    private String oriImgName; // 원본 이미지 파일명
    private String imgUrl; // 이미지 조회 경로
    private String repImgYn; // 대표 이미지 여부

    private static ModelMapper modelMapper = new ModelMapper(); // 멤버 변수로 ModelMapper 객체를 추가한다.

    public static ItemImgDTO of(ItemImg itemImg){ // ItemImg 엔티티를 매개변수로 받아 ItemImgDTO 객체를 반환해주는 메소드
        // ItemImg 엔티티 객체를 파라미터로 받아, ItemImg 객체의 필드명과 자료형이 같을 때, ItemImgDTO로 값을 복사해서 변환해주도록 한다. 반환형은 뒤에서 선언한 클래스의 클래스형이다.
        // 또한 static 메소드로 선언하여 ItemImgDTO 객체를 생성하지 않아도 호출할 수 있도록 하였다.
        return modelMapper.map(itemImg, ItemImgDTO.class);
    }
}
