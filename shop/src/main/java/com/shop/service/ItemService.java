package com.shop.service;

import com.shop.dto.ItemFormDTO;
import com.shop.entity.Item;
import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import com.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

    public Long saveItem(ItemFormDTO itemFormDTO, List<MultipartFile> itemImgFileList) throws Exception{

        // 상품 등록
        Item item = itemFormDTO.createItem(); // item Form DTO를 item 엔티티 객체로 변환
        itemRepository.save(item); // item을 컨텍스트에 저장

        // 이미지 등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);
            if(i == 0){ // 이미지 리스트의 첫 이미지인 경우
                itemImg.setRepImgYn("Y"); // 대표 이미지로 등록
            }else{
                itemImg.setRepImgYn("N");
            }
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i)); // 상품 이미지 업로드 및 정보 저장
        }

        return item.getId();
    }
}






