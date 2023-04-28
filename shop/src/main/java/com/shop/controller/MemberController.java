package com.shop.controller;

import com.shop.dto.MemberFormDTO;
import com.shop.entity.Member;
import com.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@RequestMapping("/members/*")
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDTO", new MemberFormDTO()); // 이렇게 하면 빈 객체가 넘어가서 th:field 등을 사용하기 좋다.

        return "member/memberForm";
    }

    @PostMapping(value = "/new")
    // 검증하려는 객체의 앞에 @Valid 어노테이션을 선언하고, 파라미터로 BindingResult 객체를 추가해준다.
    // 검사 후 결과는 알아서 BindingResult 객체에 들어간다.
    public String memberForm(@Valid MemberFormDTO memberFormDTO, BindingResult bindingResult, Model model){ // 폼값에 넘길 때 빈 객체를 넘기고 그걸 전부 다시 넘겨받았기에 DTO로 알아서 매핑이 될 것이다. 그리고 위에랑 메소드명 같은데 매개변수 종류가 달라서 오버로딩이다.

        if(bindingResult.hasErrors()){
            return "member/memberForm";
        }

        try {
            Member member = Member.createMember(memberFormDTO, passwordEncoder); // DTO 객체로 엔티티를 만든다
            memberService.saveMember(member); // 엔티티를 save() 한다.
        } catch (IllegalStateException e) { // 위의 saveMember() 메소드에서 실패 시 예외 발생 (validateDuplicateMember 메소드에서 IllegalStateException 던지게 구성했음)
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/login")
    public String loginMember(){
        return "/member/memberLoginForm";
    }

    // 로그인 처리에 대한 PostMapping은 할 필요 없다 (스프링 시큐리티에서 요청을 중간에 가져가서 처리해줌)
    // failureUrl 메소드로 로그인 실패 시 이동할 URL을 지정해줬었다. 그 URL의 GetMapping을 해준다.
    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/member/memberLoginForm";
    }
}








