package com.example.ex00.dependency;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor // 생성자 주입
@Data
public class Restaurant {
    private final Chef chef;
}
