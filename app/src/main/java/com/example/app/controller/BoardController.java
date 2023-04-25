package com.example.app.controller;

import com.example.app.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
