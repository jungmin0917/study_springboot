package com.shop.service;

import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {

    @Value("${itemImgLocation}") // application.properties에 등록한 값을 불러와서 변수에 저장
    private String itemImgLocation;

    private final ItemImgRepository itemImgRepository;

    private final FileService fileService; // 파일 업로드, 삭제용 서비스

    // MultipartFile : HTTP 요청에서 전송된 파일 데이터를 나타내는 객체. 이 객체를 사용하면 파일 업로드 기능을 간편하게 구현할 수 있다.
    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception{
        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        // 파일 업로드
        if(!StringUtils.isEmpty(oriImgName)){ // StringUtils는 타임리프의 유틸리티 클래스이며 이는 java.lang.String 클래스의 메서드를 확장한 것이다. 이 조건은 파일명이 존재하면이라는 뜻이다 (즉, 파일이 존재하면)
            imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes()); // 사용자가 상품의 이미지를 등록했다면 저장할 경로와 파일의 이름, 파일의 바이트 배열을 파라미터로 하여 uploadFile 메소드를 호출한다. 호출 결과 로컬에 저장된 파일의 이름을 imgName 변수에 저장한다.
            imgUrl = "/images/item/" + imgName; // 저장한 상품 이미지를 불러올 경로를 설정함. 외부 리소스를 불러오는 urlPattern으로 WebMvcConfig에서 "/images/**"로 설정해주었었다. 또한, application.properties에서 설정한 uploadPath 프로퍼티 경로인 C:/shop/ 아래 item 폴더에 이미지를 저장하므로 상품 이미지를 불러오는 경로로 /images/item/을 붙여준다.
        }
        
        // 상품 이미지 정보 저장
        // imgName : 실제 로컬에 저장된 상품 이미지 파일의 이름
        // oriImgName : 업로드했던 상품 이미지 파일의 원래 이름
        // imgUrl : 업로드 결과 로컬에 저장된 상품 이미지 파일을 불러오는 경로
        itemImg.updateItemImg(imgName, oriImgName, imgUrl);
        itemImgRepository.save(itemImg);
    }

}








