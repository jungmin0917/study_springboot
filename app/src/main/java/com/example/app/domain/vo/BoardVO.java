package com.example.app.domain.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

// 참고용
//CREATE TABLE JUNGMIN_BOARD(
//   BOARD_ID NUMBER CONSTRAINT PK_BOARD PRIMARY KEY,
//   BOARD_TITLE VARCHAR(500) NOT NULL,
//   BOARD_WRITER VARCHAR(500) NOT NULL,
//   BOARD_CONTENT VARCHAR(1000) NOT NULL,
//   BOARD_REGISTER_DATE DATE DEFAULT SYSDATE,
//   BOARD_UPDATE_DATE DATE DEFAULT SYSDATE
//);

@Component
@Data
public class BoardVO {
    private Long boardId;
    private String boardTitle;
    private String boardWriter;
    private String boardContent;
    private String boardRegisterDate;
    private String boardUpdateDate;
}









