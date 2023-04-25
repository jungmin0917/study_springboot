package com.example.app.controller;

import com.example.app.domain.vo.BoardVO;
import com.example.app.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board/*")
public class BoardController {
    private final BoardService boardService;

//    게시글 전체 목록 (나중에 페이징 추가)
    @GetMapping("list")
    public void showList(Model model){
        model.addAttribute("boards", boardService.getList());
    }


//    게시글 1개 조회
    @GetMapping("read")
    public void getBoard(Long boardId, Model model){
        model.addAttribute("board", boardService.getBoard(boardId));
    }

//    게시글 추가 (완료버튼 눌렀을 때)
    @PostMapping("write")
    public RedirectView write(BoardVO boardVO){
        boardService.write(boardVO); // 글 작성

        // 보통 글 작성 완료 후 목록으로 간다
        // 여기서 forward, redirect에 대한 개념을 잘 알아야 한다
        // 글 작성 같은 화면에서 forward를 사용하면 똑같은 요청이 또 갈 수가 있다 (새로고침할 때마다 작성이 된다든지)
        // 그리고 그냥 forward 방식으로 /board/list라고 return하면,
        // 이게 해당 컨트롤러로 가는 게 아니고 view쪽의 list.html로 가게 된다.

        return new RedirectView("/board/list"); // 이렇게 넘어가면 해당 컨트롤러로 이동함
    }

//    게시글 삭제

//    게시글 수정

}
