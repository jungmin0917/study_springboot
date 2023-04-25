package com.example.app.service;

import com.example.app.domain.dao.BoardDAO;
import com.example.app.domain.vo.BoardVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardDAO boardDAO;

    //    게시글 1개 조회
    public BoardVO getBoard(Long boardId){
        return boardDAO.findById(boardId);
    }

    //    게시글 목록 조회
    public List<BoardVO> getList(){
        return boardDAO.findAll();
    }

    //    게시글 추가
    public void write(BoardVO boardVO){
        boardDAO.save(boardVO);
    }

    //    게시글 삭제
    public void remove(Long boardId){
        boardDAO.delete(boardId);
    }

    //    게시글 수정
    public void modify(BoardVO boardVO){
        boardDAO.setBoard(boardVO);
    }

}

















