package com.example.app.mapper;

import com.example.app.domain.vo.BoardVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
//    게시글 1개 조회
    public BoardVO select(Long boardId);

//    게시글 목록 조회


//    게시글 추가


//    게시글 삭제

}
