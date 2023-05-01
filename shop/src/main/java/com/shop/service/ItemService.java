package com.shop.service;

import com.shop.dto.ItemFormDTO;
import com.shop.dto.ItemImgDTO;
import com.shop.entity.Item;
import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import com.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional // 기본값이 변경 감지임
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

    // 상품 등록 메소드
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
    
    @Transactional(readOnly = true) // 상품 데이터를 읽어오는 트랜잭션은 읽기 전용으로 설정함. 이렇게 하면 JPA가 변경감지를 수행하지 않아 리소스를 절약할 수 있음
    // 등록된 상품을 이미지 리스트까지 읽어서 ItemFormDTO에 모두 담아 반환하는 메소드
    // 상품 읽기 메소드
    public ItemFormDTO getItemDtl(Long itemId){ // 여기서 ItemDtl에서 Dtl은 Detail의 줄임말이다.
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId); // 해당 상품의 이미지를 조회한다. 등록순으로 가지고 오기 위해서 상품 이미지 아이디 오름차순으로 가져옴.

        List<ItemImgDTO> itemImgDTOList = new ArrayList<>();
        for(ItemImg itemImg : itemImgList){
            ItemImgDTO itemImgDTO = ItemImgDTO.of(itemImg); // ItemImg 엔티티를 ItemImgDTO 객체로 반환

            itemImgDTOList.add(itemImgDTO);
        }

        Item item = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);
        ItemFormDTO itemFormDTO = ItemFormDTO.of(item);
        itemFormDTO.setItemImgDTOList(itemImgDTOList);

        return itemFormDTO;
    }

    public Long updateItem(ItemFormDTO itemFormDTO, List<MultipartFile> itemImgFileList) throws Exception{

        // 상품 수정
        Item item = itemRepository.findById(itemFormDTO.getId())
                .orElseThrow(EntityNotFoundException::new); // 상품 등록 화면으로부터 전달받은 상품 아이디를 이용해 상품 엔티티 조회
        item.updateItem(itemFormDTO); // 해당 Id를 가진 상품 엔티티를 itemFormDTO의 값을 이용하여 수정함 (자동으로 update 쿼리가 간다)

        List<Long> itemImgIds = itemFormDTO.getItemImgIds(); // 해당 상품의 이미지 아이디 리스트를 조회함. (중요한 점은, itemFormDTO에서 가져오는 것이므로 기존의 이미지 리스트가 아닌 지금 새로 올린 이미지 리스트라는 것임)

        // 이미지 등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i)); // 이미지 id와 파일 넘겨서 상품 이미지를 업데이트함
        }

        return item.getId();
    }
}














