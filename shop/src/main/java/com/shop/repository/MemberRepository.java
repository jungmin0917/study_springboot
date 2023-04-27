package com.shop.repository;


import com.shop.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 회원가입 시 중복된 회원이 있는지 검사하기 위해 이메일로 회원을 조회한다.
    Member findByEmail(String email); // 이메일을 파라미터로 받아서 조회한 후 Member 엔티티로 반환함

}
