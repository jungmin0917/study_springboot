package com.shop.entity;

import com.shop.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Slf4j
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
public class MemberTest {

    @Autowired
    private MemberRepository memberRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    @DisplayName("Auditing 테스트")
    @WithMockUser(username = "gildong", roles = "USER") // 스프링 시큐리티에서 제공하는 어노테이션으로, 지정한 사용자가 로그인한 상태라고 가정하고 테스트를 진행할 수 있음
    public void auditingTest(){
        Member newMember = new Member();
        memberRepository.save(newMember); // 아무 정보 안 넣고 컨텍스트에 저장

        em.flush(); // 컨텍스트를 DB에 반영
        em.clear(); // 컨텍스트 초기화

        Member member = memberRepository.findById(newMember.getId()) // 아까 저장한 아이디로 DB에서 검색함
                .orElseThrow(EntityNotFoundException::new);

        // regTime이나 다른 컬럼들을 출력해보면서 실제로 공통 컬럼들의 값이 자동 추가됐는지 확인해보자
        System.out.println("register time : " + member.getRegTime());
        System.out.println("update time : " + member.getUpdateTime());
        System.out.println("create member : " + member.getCreatedBy());
        System.out.println("modify member : " + member.getModifiedBy());
    }
}










