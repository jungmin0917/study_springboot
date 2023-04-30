package com.shop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${uploadPath}") // properties에 설정한 해당 프로퍼티 값을 가져옴
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**") // 웹 브라우저에 입력하는 url이 /images로 시작하는 경우,  uploadPath에 설정한 폴더를 기준으로 파일을 읽어오도록 설정함
                .addResourceLocations(uploadPath); // 로컬 컴퓨터에 저장된 파일을 읽어올 루트 경로를 설정함.
    }
}
