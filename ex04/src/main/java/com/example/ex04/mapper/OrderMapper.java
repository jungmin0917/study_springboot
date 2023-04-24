package com.example.ex04.mapper;

import com.example.ex04.domain.OrderDTO;
import com.example.ex04.domain.OrderVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
//    주문하기
    public void insert(OrderVO orderVO);

//    주문 내역
    public List<OrderDTO> selectAll(String sort);
}
