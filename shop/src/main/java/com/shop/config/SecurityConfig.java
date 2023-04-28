package com.shop.config;

import com.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 해당 클래스가 스프링 Bean 구성을 위한 구성(설정) 클래스임을 나타냄
@EnableWebSecurity // WebSEcurityConfigurerAdapter를 상속받는 클래스에 이 어노테이션을 선언하면, SpringSecurityFilterChain이 자동으로 포함된다. WebSEcurityConfigurerAdapter를 상속받아서 메소드 오버라이딩을 통해 기본 보안 설정을 수정하여 커스텀할 수 있다.
public class SecurityConfig extends WebSecurityConfigurerAdapter { // WebSecurityConfigurerAdapter를 상속받는다

    @Autowired
    private MemberService memberService;

    @Override
    // HTTP 요청에 대한 보안을 설정한다. 페이지 권한 설정, 로그인 페이지 설정, 로그아웃 메소드 등에 대한 설정을 작성한다.
    protected void configure(HttpSecurity http) throws Exception {

        // 아래 HttpSecurity 객체의 formLogin() 메소드로 특정 페이지를 로그인 폼으로 활성화시킨다.
        // 메소드들은 외울 필요는 없고 어차피 쓰다 보면 자주 쓰는 건 외워짐
        // 주의 : URL 입력 시 /부터 써서 URI 전체 경로를 쓰도록 한다 (절대경로로)
        http.formLogin()// 로그인 관련 설정
                .loginPage("/members/login") // 로그인 페이지 지정 (당연히 POST로 넘어옴)
                .defaultSuccessUrl("/") // 성공 시 리다이렉트할 페이지 지정
                .usernameParameter("email") // 로그인 시 사용할 파라미터 이름 지정
                .failureUrl("/members/login/error") // 로그인 실패 시 이동할 페이지 지정
                .and()// HttpSecurity.formLogin()과 HttpSecurity.logout() 설정을 연결해서 한 번에 쓰기 위한 것
                .logout()// 로그아웃 관련 설정
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) // 로그아웃 URL 지정
                .logoutSuccessUrl("/"); // 로그아웃 성공 시 이동할 URL 지정

        // 위에서 .and()는 각 HttpSecurity 객체의 메소드를 연결(여러 기능을 함께 구성)하기 위한 것이다.
        // 먼저 http.formLogin()으로 로그인 폼 및 로그인에 대해 설정했고,
        // http.logout()으로 로그아웃에 대해 설정한 것이다.
        // 이것을 .and() 메소드로 이어서 연결한 것이다.

        http.authorizeRequests() // 요청을 받아 시큐리티를 처리함 (HttpServletRequest 객체를 이용함)
                // 메인 페이지(/), 회원 관련 URL, 상품 상세 페이지, 상품 이미지 경로에 대해 permitAll을 한다 (인증 없이 접근 가능)
                .mvcMatchers("/", "/members/**","/item/**", "/images/**").permitAll()
                // admin으로 시작하는 경로는 해당 계정이 ADMIN Role일 때만 접근 가능하도록 설정
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                // 다른 모든 경로는 모두 인증을 요구하도록 설정
                .anyRequest().authenticated();

        // 인증 예외에 대한 처리 구성
        http.exceptionHandling()
                // 인증되지 않은 사용자가 보호된 자원에 접근하려고 할 때 클라이언트에게 보낼 응답을 구성
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 해당 경로에 포함된 파일들은 인증을 무시하도록 설정함
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }

    @Bean // Bean 어노테이션은 주로 Configuration이 붙은 클래스 내에서 사용되며, 이 클래스를 다른 Bean에서도 사용할 수 있게 하는 어노테이션이다.
    // 비밀번호를 DB에 그대로 저장하면 DB가 해킹됐을 경우 비밀번호가 그대로 노출된다. 이를 해결하기 위해 BCryptPasswordEncoder의 해시 함수를 이용하여 비밀번호를 암호화하여 저장한다.
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    // 스프링 시큐리티에서 인증은 AuthentificationManager를 통해 이루어지며, AuthentificationManagerBuilder가 AuthentificationManager를 생성해준다.
    // 즉 아래 메소드는 인증을 사용하기 위해 작성한 메서드이다.
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService)
                .passwordEncoder(passwordEncoder()); // userDetailsService를 구현하고 있는 객체로 memberService를 지정해주고, 비밀번호 암호화를 위해 passwordEncoder를 지정해준다.
    }
}







