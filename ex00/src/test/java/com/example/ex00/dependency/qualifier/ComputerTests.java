package com.example.ex00.dependency.qualifier;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ComputerTests {


//    @Autowired
//    @Qualifier("desktop")
//    Computer computer;

    @Autowired
    @Qualifier("desktop")
    Computer desktop;

    @Autowired
    @Qualifier("laptop")
    Computer laptop;

    @Autowired
    Computer computer; // 기본값으로 주입받기

    @Test
    public void computerTest(){
        log.info(String.valueOf(desktop.getScreenWidth()));
        log.info(String.valueOf(laptop.getScreenWidth()));
        log.info(String.valueOf(computer.getScreenWidth()));
    }
}
