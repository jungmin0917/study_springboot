package com.shop.service;

import com.shop.entity.Member;
import com.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional // 로직을 처리하다가 에러가 발생하면 변경된 데이터를 이전 데이터로 롤백시켜줌
@RequiredArgsConstructor
public class MemberService {
    // 웬만하면 필드 주입 말고 지금처럼 생성자 주입
    private final MemberRepository memberRepository; // 레포지토리 주입 받음

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
}
