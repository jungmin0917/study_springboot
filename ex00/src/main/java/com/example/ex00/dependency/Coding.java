package com.example.ex00.dependency;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component // 자동 주입받는 필드를 가진 클래스 자체도 스프링 빈에 등록되어 있어야 자동주입을 받을 수 있다!!
@Data
public class Coding {
//    기본적으로 아래와 같이 적으면 new 생성자가 없으므로 null값이 computer에 들어가 있을 것이다.
//    private Computer computer;

//    하지만 여기서 어노테이션을 통해 computer 객체가 필요하다고 요청을 할 수 있다 (스프링 컨테이너에게)
//    ApplicationContext에게 요청을 한다

//    정말로 위와 같이 Computer computer만 적으면 Coding을 생성 시 computer 필드에 null값이 들어가 있는지 테스트부터 해보자.
//    private Computer computer = new Computer(); // 이렇게 하면 의존성이 생김

//    필드 주입. 직접적으로 해당 필드에 대한 주입을 요청을 하는 것이다.
//    Context라는 영역에 활성화, 비활성화가 있다. 처음에는 비활성화였다가 요청을 받으면 해당 Context 영역을 메모리에 올리는 것이다.

//    필드 주입은 굉장히 편하게 주입할 수 있으나, 순환 참조(무한 루프) 시 오류가 발생하지 않는다.
//    그래서 StackOverFlow가 발생할 수가 있다
//    A 객체 안에 B 객체 안에 A 객체 안에... 이렇게 되면 순환 참조가 일어나서 메모리 누수가 발생한다.
//    근데 그것을 스프링이 Exception을 뿜어내지 않기 때문에 결국 컴퓨터가 뻗을 것이다.
//    그래서 생성자 주입이나 세터 주입이 있는데 보통 생성자 주입을 가장 많이 쓴다.

    @Autowired
    private Computer computer;
}
