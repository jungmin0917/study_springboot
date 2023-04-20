package com.example.ex00.dependency;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ComputerTests {

    // 메소드 안에서 new로 직접 생성 시 자동 주입을 받을 수 없다.
    // 그렇기 때문에 아래와 같이 주입받을 객체를 선언만 하고, Autowired 어노테이션을 걸어주면 자동주입을 받을 수 있다.
    // 단위 테스트에서는 필드 주입만 사용 가능하다.

    @Autowired
    Coding coding; // 이렇게 하면 coding 객체를 스프링 IoC가 관리하게 된다.

    @Test
    public void computerTest(){
//        절대 메소드 안의 지역변수에 @Autowired 걸지 말 것
//        아래와 같이 new로 직접 생성하면, 스프링 IoC가 관리하는 객체가 아니기 때문에, 자동 주입을 받을 수 없게 된다.
//        Coding coding = new Coding();
        log.info(coding.getComputer().toString());
    }
}
