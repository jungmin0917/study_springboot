package com.example.ex01.mapper;

import com.example.ex01.domain.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
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

        productVO.setProductName("무");
        productVO.setProductStock(500L);
        productVO.setProductPrice(1000L);

        productMapper.insert(productVO);
    }

    @Test
    public void selectTest(){
        Long productId = 2L;

        log.info(productMapper.select(productId).toString());
    }

    @Test
    public void updateTest(){
        ProductVO productVO = productMapper.select(2L);

        log.info(productVO.toString());

        productVO.setProductName("고구마");
        productVO.setProductStock(300L);
        productVO.setProductPrice(5000L);

        productMapper.update(productVO);
    }

    @Test
    public void deleteTest(){
        Long productId = 1L;

        productMapper.delete(productId);
    }

    @Test
    public void selectAllTest(){
//        productMapper.selectAll().stream().forEach(productVO -> log.info(productVO.toString()));
//        productMapper.selectAll().stream().map(productVO -> productVO.toString()).forEach(productVO -> log.info(productVO));
//        productMapper.selectAll().stream().map(ProductVO::toString).forEach(log::info);
        // 세개 다 똑같은 행위를 하지만, 맨 아래처럼 메소드 레퍼런스를 사용하여, 각 요소가 클래스::메소드 이렇게 쓴 메소드를 참조하여 실행하도록 할 수 있다.

//        Assertions.assertThat(productMapper.selectAll().get(0).getProductName()).isEqualTo("감자");
        // 위의 assertThat 메소드는 테스트 결과를 검증할 때 사용한다
        
        Assertions.assertThat(productMapper.select(4L)).isIn(productMapper.selectAll());
        // isIn 메소드 : 주어진 값이 리스트나 배열 등의 컬렉션에 포함되어 있는지 확인
    }
}
