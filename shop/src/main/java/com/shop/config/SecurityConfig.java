package com.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // 해당 클래스가 스프링 Bean 구성을 위한 구성(설정) 클래스임을 나타냄
@EnableWebSecurity // WebSEcurityConfigurerAdapter를 상속받는 클래스에 이 어노테이션을 선언하면, SpringSecurityFilterChain이 자동으로 포함된다. WebSEcurityConfigurerAdapter를 상속받아서 메소드 오버라이딩을 통해 기본 보안 설정을 수정하여 커스텀할 수 있다.
public class SecurityConfig extends WebSecurityConfigurerAdapter { // WebSecurityConfigurerAdapter를 상속받는다

    @Override
    // HTTP 요청에 대한 보안을 설정한다. 페이지 권한 설정, 로그인 페이지 설정, 로그아웃 메소드 등에 대한 설정을 작성한다.
    protected void configure(HttpSecurity http) throws Exception {
        // 일단 비워둠
    }

    @Bean // Bean 어노테이션은 주로 Configuration이 붙은 클래스 내에서 사용되며, 이 클래스를 다른 Bean에서도 사용할 수 있게 하는 어노테이션이다.
    // 비밀번호를 DB에 그대로 저장하면 DB가 해킹됐을 경우 비밀번호가 그대로 노출된다. 이를 해결하기 위해 BCryptPasswordEncoder의 해시 함수를 이용하여 비밀번호를 암호화하여 저장한다.
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
