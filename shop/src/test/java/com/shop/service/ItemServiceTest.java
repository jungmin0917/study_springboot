package com.shop.service;

import com.shop.constant.ItemSellStatus;
import com.shop.dto.ItemFormDTO;
import com.shop.entity.Item;
import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import com.shop.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
public class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemImgRepository itemImgRepository;


    // MockMultipartFile 클래스를 이용해 가짜 MultipartFile 리스트를 만들어서 반환해주는 메소드.
    public List<MultipartFile> createMultipartFiles() throws Exception{
        List<MultipartFile> multipartFileList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            String path = "C:/shop/item/";
            String imageName = "image" + i + ".jpg";
            MockMultipartFile multipartFile = new MockMultipartFile(path, imageName, "image/jpg", new byte[]{1, 2, 3, 4});

            multipartFileList.add(multipartFile);
        }

        return multipartFileList;
    }

    @Test
    @DisplayName("상품 등록 테스트")
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void saveItem() throws Exception{

        ItemFormDTO itemFormDTO = new ItemFormDTO(); // 상품 등록 화면에서 입력 받는 상품 데이터를 세팅해준다
        itemFormDTO.setItemNm("테스트 상품");
        itemFormDTO.setItemSellStatus(ItemSellStatus.SELL);
        itemFormDTO.setItemDetail("테스트 상품입니다.");
        itemFormDTO.setPrice(1000);
        itemFormDTO.setStockNumber(100);

        List<MultipartFile> multipartFileList = createMultipartFiles();
        Long itemId = itemService.saveItem(itemFormDTO, multipartFileList); // 상품 데이터와 이미지 정보를 파라미터로 넘겨서 저장 후 저장된 상품의 아이디 값을 반환

        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId); // 아까 저장한 상품의 아이디로 itemImg 엔티티를 조회해서 List에 담음
        Item item = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new); // item 엔티티 조회

        assertEquals(itemFormDTO.getItemNm(), item.getItemNm()); // 상품 저장 후 조회해온 엔티티의 아이템명과 입력받은 아이템명이 같은지 확인
        assertEquals(itemFormDTO.getItemSellStatus(), item.getItemSellStatus());
        assertEquals(itemFormDTO.getItemDetail(), item.getItemDetail());
        assertEquals(itemFormDTO.getPrice(), item.getPrice());
        assertEquals(itemFormDTO.getStockNumber(), item.getStockNumber());
        assertEquals(multipartFileList.get(0).getOriginalFilename(), itemImgList.get(0).getOriImgName());
        
        // 컨텍스트에 저장 후 불러온 엔티티의 속성과 입력받은 속성이 같은지 전부 확인해 봄
    }

}











