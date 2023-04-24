package com.example.ex03.mapper;

import com.example.ex03.domain.OrderVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
//    주문하기
    public void insert(OrderVO orderVO);

//
}
