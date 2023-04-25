package com.example.app.controller;

import com.example.app.domain.vo.BoardVO;
import com.example.app.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
//    forward 방식은 처음 요청한 경로가 마지막까지 남아있고,
//    redirect 방식은 마지막에 요청한 경로가 남아있다.
    @PostMapping("write")
    public RedirectView write(BoardVO boardVO, RedirectAttributes redirectAttributes){
        boardService.write(boardVO); // 글 작성

        // 보통 글 작성 완료 후 목록으로 간다
        // 여기서 forward, redirect에 대한 개념을 잘 알아야 한다
        // 글 작성 같은 화면에서 forward를 사용하면 똑같은 요청이 또 갈 수가 있다 (새로고침할 때마다 작성이 된다든지)
        // 그리고 그냥 forward 방식으로 /board/list라고 return하면,
        // 이게 해당 컨트롤러로 가는 게 아니고 view쪽의 list.html로 가게 된다.

        // redirect 방식일 때 화면에 데이터를 넘겨줄 수 있는 방법은 쿼리스트링에 넣는 방식이 있었다.
        // 두 번째 방식으로는 Session을 사용하는 방법이 있다.

        // RedirectAttributes 객체가 리다이렉트 시 데이터를 넘기는 두 가지 방식을 지원한다
        // RedirectAttributes.addAttribute() : 쿼리스트링으로 보낼 때 사용 (컨트롤러에서 사용할 때)
        // RedirectAttributes.addFlashAttribute() : 세션의 Flash 영역으로 보낼 때 사용 (화면에서 사용할 때)

        redirectAttributes.addAttribute("boardId", boardVO.getBoardId());
        redirectAttributes.addFlashAttribute("boardId", boardVO.getBoardId());
        // 방금 작성한 글의 boardId를 다음 화면에 넘기겠다는 뜻 (당장 이 컨트롤러에선 필요 없으나 테스트용으로 적은 듯)

        // RedirectView를 리턴하면 ViewResolver가 아니고 다시 Controller를 찾아간다.
        return new RedirectView("/board/list"); // 이렇게 넘어가면 해당 컨트롤러로 이동함
        
    }

//    게시글 삭제

//    게시글 수정

}
