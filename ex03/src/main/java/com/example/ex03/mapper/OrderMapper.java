package com.example.ex03.mapper;

import com.example.ex03.domain.OrderDTO;
import com.example.ex03.domain.OrderVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
//    주문하기
    public void insert(OrderVO orderVO);

//    주문 내역
    public List<OrderDTO> selectAll();
}
