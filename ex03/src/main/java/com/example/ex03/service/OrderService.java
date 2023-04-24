package com.example.ex03.service;

// 원래 인터페이스 쓰지만 현재는 편의를 위해 그냥 클래스로 바로 구현한다

import com.example.ex03.dao.OrderDAO;
import com.example.ex03.domain.OrderDTO;
import com.example.ex03.dao.ProductDAO;
import com.example.ex03.domain.OrderVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // 생성자 주입
public class OrderService {

    private final OrderDAO orderDAO;
    private final ProductDAO productDAO; // 상품 재고 차감하기 위해 필요함
    // 이게 DAO-Service 간의 특징임
    // 각 테이블을 건드리는 것은 각 Mapper 및 각 DAO에 맞춰서 넣지만,
    // 서비스에서는 해당 로직에 필요한 DAO들을 불러와서 여러 개를 모아서 사용하는 것임

//    주문하기
    public void order(OrderVO orderVO){
        // 여기서 @Transactional 어노테이션을 사용하는 것이다.
        // 이 어노테이션을 메소드 레벨에 선언하면 해당 메소드에서 실행되는 모든 쿼리는 하나의 트랜잭션 범위 안에서 실행된다
        productDAO.setProductStock(orderVO); // 먼저 해당 상품 재고를 먼저 차감한다
        orderDAO.save(orderVO); // 그리고 주문을 넣는다

        // 이렇게 여러 쿼리를 하나의 서비스에 맞게 모아서 돌리는 것이 바로 Service 계층의 목적이다.
    }

//    주문 내역
    public List<OrderDTO> getList(String sort){
        return orderDAO.findAll(sort);
    }
}









