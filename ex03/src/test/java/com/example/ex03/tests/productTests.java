package com.example.ex03.tests;

import com.example.ex03.domain.ProductVO;
import com.example.ex03.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class productTests {

    @Autowired
    private ProductService productService;

        @Test
        public void insertTest(){
            ProductVO productVO = new ProductVO();

            productVO.setProductName("양상추");
            productVO.setProductStock(300);
            productVO.setProductPrice(4000);

            productService.register(productVO);

            productService.getList().stream().map(ProductVO::toString).forEach(log::info);
    }
}














