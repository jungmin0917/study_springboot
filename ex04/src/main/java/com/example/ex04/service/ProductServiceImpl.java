package com.example.ex04.service;

import com.example.ex04.dao.ProductDAO;
import com.example.ex04.domain.ProductVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // 생성자 주입
public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;

    @Override
    public void register(ProductVO productVO) {
        // 지금은 여기에 쿼리 하나만 있지만, 실무에서는 Service 하나에 여러 DAO의 메소드를 쓰므로
        // 여러 쿼리를 실행하게 될 수 있는 것이다.
        // 이것을 추상화하여 우리가 관리하니까, 즉 나눠서 개발하니까 분업도 편하고 유지보수성이 좋다.
        productDAO.save(productVO);
    }

    @Override
    public List<ProductVO> getList() {
        return productDAO.findAll();
    }

    @Override
    public ProductVO getProduct(Long productId) {
        return productDAO.findById(productId);
    }
}
