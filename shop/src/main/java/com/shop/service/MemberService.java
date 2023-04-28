package com.shop.service;

import com.shop.entity.Member;
import com.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional // 로직을 처리하다가 에러가 발생하면 변경된 데이터를 이전 데이터로 롤백시켜줌
@RequiredArgsConstructor
public class MemberService implements UserDetailsService { // UserDetailsService 인터페이스를 구현함으로써 이 클래스를 통해 로그인/로그아웃을 구현한다고 생각하면 된다.
    // 웬만하면 필드 주입 말고 지금처럼 생성자 주입
    private final MemberRepository memberRepository; // 레포지토리 주입 받음

    // 회원가입 처리 메소드
    public Member saveMember(Member member){
        validateDuplicateMember(member); // 가입된 회원인지 검사 (유효성 검사)
        return memberRepository.save(member); // 영속성 컨텍스트에 저장
    }

    // 가입된 회원인지 이메일로 체크하는 메소드 (여기 클래스 안에서만 쓰므로 private 지정자)
    private void validateDuplicateMember(Member member){
        Member findMember = memberRepository.findByEmail(member.getEmail());
        // Member 엔티티의 이메일을 가져와서 Member 엔티티를 반환받는다.

        if(findMember != null){ // 조회된 데이터가 있으면
            throw new IllegalStateException("이미 가입된 회원입니다");
        }
    }

    // UserDetailsService 인터페이스를 구현했으므로 반드시 오버라이딩해야 함
    // 기본 매개변수는 name이지만 email로 바꿨음 (엔티티의 unique 속성을 갖고 있는 필드 쓰면 됨)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email); // 이메일로 회원 조회

        if(member == null){ // 조회된 회원이 없으면
            throw new UsernameNotFoundException(email); // UsernameNotFoundException을 던짐
        }

        // 조회된 회원이 있으면 UserDetails를 구현하고 있는 User 객체 반환함
        // 스프링 시큐리티의 User.builder() 메소드를 이용하여 UserDetails를 구현하고 있는 User 객체를 반환한다.
        // User 객체를 생성하기 위해 이메일, 비밀번호, Role을 파라미터로 넘겨줌 (이름이랑 주소는 필요 없는 듯?)
        // 이메일과 패스워드로 로그인하기 때문에 Email, Password를 넘겼음. (Role은 기본적으로 넘어갈 데이터)
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }
}









