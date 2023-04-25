package com.example.app.mapper;

import com.example.app.domain.vo.BoardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
//    게시글 1개 조회
    public BoardVO select(Long boardId);

//    게시글 목록 조회
    public List<BoardVO> selectAll();

//    게시글 추가
    public void insert(BoardVO boardVO);

//    게시글 삭제
    public void delete(Long boardId);

//    게시글 수정
    public void update(BoardVO boardVO);
}
