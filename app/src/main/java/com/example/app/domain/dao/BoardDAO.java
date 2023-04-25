package com.example.app.domain.dao;

import com.example.app.domain.vo.BoardVO;
import com.example.app.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardDAO {
    private final BoardMapper boardMapper; // 생성자 주입

    // 2.6 버전 이후의 스프링부트부터는 자동으로 순환참조 막힌다고 함


    //    게시글 1개 조회
    public BoardVO findById(Long boardId){
        return boardMapper.select(boardId);
    }

    //    게시글 목록 조회
    public List<BoardVO> findAll(){
        return boardMapper.selectAll();
    }

    //    게시글 추가
    public void save(BoardVO boardVO){
        boardMapper.insert(boardVO);
    }

    //    게시글 삭제
    public void delete(Long boardId){
        boardMapper.delete(boardId);
    }

    //    게시글 수정
    public void setBoard(BoardVO boardVO){
        boardMapper.update(boardVO);
    }

}










