package com.example.ex01.mapper;

import com.example.ex01.domain.ProductVO;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class MapperTests {

//    @Autowired
//    private TimeMapper timeMapper;

//    @Test
//    public void getTimeTest(){
//        log.info(timeMapper.getTime());
//    }

    @Autowired
    private ProductMapper productMapper;

    @Test
    public void insertTest(){
        ProductVO productVO = new ProductVO();

        productVO.setProductName("감자");
        productVO.setProductStock(400L);
        productVO.setProductPrice(1500L);

        productMapper.insert(productVO);
    }

    @Test
    public void selectTest(){
        Long productId = 1L;

        log.info(productMapper.select(productId).toString());

    }
}
