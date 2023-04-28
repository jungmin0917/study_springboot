package com.shop.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// AuthenticationEntryPoint 인터페이스를 구현한 커스텀AuthenticationEntryPoint 클래스를 작성한다.
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    
    @Override
    // 반드시 AuthenticationEntryPoint에 있는 단일 메소드 commence를 구현해야 함
    // commence 메소드는 인증이 실패한 경우 클라이언트에게 보낼 응답을 생성하고, HttpServletResponse 객체를 사용하여 응답을 전송
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"); // 인증 실패 시 UNAUTHORIZED 의 실패 이유로, Unauthorized라는 메시지를 전송
    }
}
