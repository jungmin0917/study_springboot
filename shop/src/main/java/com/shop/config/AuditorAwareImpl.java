package com.shop.config;


import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

// extends와 implements의 차이 : extends는 부모의 추상 클래스를 오버라이딩할 필요가 없다.
// implements느 반드시 부모의 추상 클래스들을 오버라이딩하여 사용해야 한다
public class AuditorAwareImpl implements AuditorAware<String> { // AuditorAware 인터페이스는 등록자(사용자) 데이터를 자동으로 생성할 때 사용하는 인터페이스이다.

    @Override
    // getCurrentAuditor() 메소드를 구현하였다. 현재 인증 정보를 스프링 시큐리티의 메소드로 가져와서 이를 Optional 객체로 반환해주는 메소드이다.
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 스프링 시큐리티로 현재 인증된 사용자 (로그인한 사용자)의 인증 정보를 가져옴.

        String userId = "";
        if(authentication != null){ // 사용자가 로그인 한 상태라면
            userId = authentication.getName();
        }

        // 여기서 Optional<T> 클래스는 null이 올 수 있는 값을 감싸는 Wrapper 클래스로, 값이 null이더라도 NPE가 발생하지 않도록 도와준다.
        // 즉 여기서 Optional<String> 클래스는 기본적으로 String 클래스를 Wrapping해주는 클래스이다.
        return Optional.of(userId);
    }
}
