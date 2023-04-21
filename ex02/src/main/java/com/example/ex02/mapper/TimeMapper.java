package com.example.ex02.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper // 현재 인터페이스가 매퍼 인터페이스임을 명시
public interface TimeMapper {
    public String getTime();
}
