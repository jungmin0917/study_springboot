package com.example.app.mapper;

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
        assertThat(boardMapper.select(1L).getBoardTitle()).isEqualTo("테스트 제목1");

        // assertThat(검증할 값).isEqualTo(비교할 값) 으로 해당 검증값이 비교할 값과 같은지 확인할 수 있다.
        // 정상적으로 검증되었으면 에러가 나지 않고 테스트가 통과한다.
    }
}
