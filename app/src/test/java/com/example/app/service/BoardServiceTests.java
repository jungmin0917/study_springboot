package com.example.app.service;

import com.example.app.domain.vo.BoardVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void getBoardTest(){
        assertThat(boardService.getBoard(6L).getBoardTitle()).isEqualTo("수정된 제목");

        // assertThat(검증할 값).isEqualTo(비교할 값) 으로 해당 검증값이 비교할 값과 같은지 확인할 수 있다.
        // 정상적으로 검증되었으면 에러가 나지 않고 테스트가 통과한다.
    }

    @Test
    public void getListTest(){
        boardService.getList().stream().map(BoardVO::toString).forEach(log::info);

        assertThat(boardService.getList().size()).isEqualTo(3);
    }

    @Test
    public void writeTest(){
        BoardVO boardVO = new BoardVO();
        boardVO.setBoardTitle("테스트 제목6");
        boardVO.setBoardWriter("테스트 작성자6");
        boardVO.setBoardContent("테스트 내용6");

        boardService.write(boardVO);

        // 지금 boardId는 BoardVO 객체를 만들고 따로 세팅하지 않았지만,
        // insert 쿼리 이후에 selectKey로 인해 자동으로 boardId가 세팅되었는지 확인해보자.

        assertThat(boardVO.getBoardId()).isEqualTo(6L);
    }

    @Test
    public void removeTest(){
        boardService.remove(5L);

        boardService.getList();
    }

    @Test
    public void modifyTest(){
        BoardVO boardVO = boardService.getBoard(6L);

        boardVO.setBoardTitle("수정된 제목");
        boardVO.setBoardContent("수정된 내용");

        boardService.modify(boardVO);

    }
}
