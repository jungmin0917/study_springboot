package com.example.app.dao;

import com.example.app.domain.dao.BoardDAO;
import com.example.app.domain.vo.BoardVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class BoardDaoTests {

    @Autowired
    private BoardDAO boardDAO;

    // Assertion 써서 반환값 내가 생각한 그 반환값이 맞는지 검증해보기
    // assertj의 Assertions 객체는 반환값에 대한 검증을 할 수 있게 해준다.
    @Test
    public void findByIdTest(){
        assertThat(boardDAO.findById(3L).getBoardTitle()).isEqualTo("수정된 제목");

        // assertThat(검증할 값).isEqualTo(비교할 값) 으로 해당 검증값이 비교할 값과 같은지 확인할 수 있다.
        // 정상적으로 검증되었으면 에러가 나지 않고 테스트가 통과한다.
    }

    @Test
    public void findAllTest(){
        boardDAO.findAll().stream().map(BoardVO::toString).forEach(log::info);

        assertThat(boardDAO.findAll().size()).isEqualTo(3);
    }

    @Test
    public void saveTest(){
        BoardVO boardVO = new BoardVO();
        boardVO.setBoardTitle("테스트 제목4");
        boardVO.setBoardWriter("테스트 작성자4");
        boardVO.setBoardContent("테스트 내용4");

        boardDAO.save(boardVO);

        // 지금 boardId는 BoardVO 객체를 만들고 따로 세팅하지 않았지만,
        // insert 쿼리 이후에 selectKey로 인해 자동으로 boardId가 세팅되었는지 확인해보자.

        assertThat(boardVO.getBoardId()).isEqualTo(4L);
    }

    @Test
    public void deleteTest(){
        boardDAO.delete(5L);

        boardDAO.findAll();
    }

    @Test
    public void setBoardTest(){
        BoardVO boardVO = boardDAO.findById(2L);

        boardVO.setBoardTitle("수정된 제목");
        boardVO.setBoardContent("수정된 내용");

        boardDAO.setBoard(boardVO);

    }
}
