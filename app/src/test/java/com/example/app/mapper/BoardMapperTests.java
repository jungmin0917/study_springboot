package com.example.app.mapper;

import com.example.app.domain.vo.BoardVO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class BoardMapperTests {

    @Autowired
    private BoardMapper boardMapper;

    // Assertion 써서 반환값 내가 생각한 그 반환값이 맞는지 검증해보기
    // assertj의 Assertions 객체는 반환값에 대한 검증을 할 수 있게 해준다.
    @Test
    public void selectTest(){
        assertThat(boardMapper.select(3L).getBoardTitle()).isEqualTo("수정된 제목");

        // assertThat(검증할 값).isEqualTo(비교할 값) 으로 해당 검증값이 비교할 값과 같은지 확인할 수 있다.
        // 정상적으로 검증되었으면 에러가 나지 않고 테스트가 통과한다.
    }

    @Test
    public void selectAllTest(){
        boardMapper.selectAll().stream().map(BoardVO::toString).forEach(log::info);

        assertThat(boardMapper.selectAll().size()).isEqualTo(3);
    }

    @Test
    public void insertTest(){
        BoardVO boardVO = new BoardVO();
        boardVO.setBoardTitle("테스트 제목4");
        boardVO.setBoardWriter("테스트 작성자4");
        boardVO.setBoardContent("테스트 내용4");

        boardMapper.insert(boardVO);

        // 지금 boardId는 BoardVO 객체를 만들고 따로 세팅하지 않았지만,
        // insert 쿼리 이후에 selectKey로 인해 자동으로 boardId가 세팅되었는지 확인해보자.

        assertThat(boardVO.getBoardId()).isEqualTo(4L);

//        boardMapper.selectAll().stream().map(BoardVO::toString).forEach(log::info);
    }

    @Test
    public void deleteTest(){
        boardMapper.delete(4L);

        boardMapper.selectAll();
    }

    @Test
    public void updateTest(){
        BoardVO boardVO = boardMapper.select(3L);

        boardVO.setBoardTitle("수정된 제목");
        boardVO.setBoardContent("수정된 내용");

        boardMapper.update(boardVO);

    }
}















