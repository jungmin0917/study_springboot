package com.example.ex00.dependency;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component // 자동 주입받는 필드를 가진 클래스 자체도 스프링 빈에 등록되어 있어야 자동주입을 받을 수 있다!!
@Data
//@NoArgsConstructor // 기본 생성자를 자동으로 만들어 줌
@RequiredArgsConstructor // final이 붙거나 @NonNull이 붙은 객체에 대한 생성자를 자동으로 만들어 줌
//@AllArgsConstructor // 전체 필드에 대한 생성자를 자동으로 만들어 줌

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

//    객체는 웬만하면 한 번 주입을 받으면 다른 곳에서 변형이 되면 안 된다.
//    근데 getter, setter가 있으면 언제든지 다른 곳에서 변형이 가능하니까, 필드 주입을 할 땐 반드시 final을 붙여야 한다.
//    final 키워드를 붙이면 그 객체에 불변성(immutable)을 제공해준다.
//    근데 필드 주입할 때 final을 붙이면 오류가 난다
//    왜냐면 필드 주입은 생성자를 통해 field에 올라간 다음에 요청이 들어가기 때문에, 초기값을 생성하기도 전에 final이 있으니 오류가 난다. 그래서 field를 붙이긴 해야 하는데 그걸 붙이면 필드 주입이 되지 않으니 좋지 않다는 것이다.
//    그렇다고 붙이지 않기에는 다른 곳에서 변형이 가능하므로 좋지가 않다.
//     @Autowired
//    private final Computer computer;

//    생성자 주입

//    Coding 생성자에 해당 필드가 있으면, 초기화될 때 같이 생성자를 생성하기 때문에, 필드까지 자동 주입이 가능한 것이다.
//    생성자 위에 @Autowired를 붙이지 않아도 자동으로 주입이 된다고 뜬다.
//    굳이 Autowired를 하지 않아도, Coding이라는 기본 생성자에 주입받을 필드 객체를 넣으면, 해당 객체가 스프링 컨테이너가 관리하는 객체라면, 자동 주입을 알아서 받는다.
//    자동 주입은, 필요한 곳에서 Autowired를 받아서 쓰면 된다.
//    초기화 생성자만 잘 작성해주면, 내부 필드 객체들도 주입받을 수 있다. 이것이 생성자 주입이다.

//    순환 참조 시 컴파일러 인지 가능, 오류 발생
//    메모리에 할당하면서 초기값으로 주입되므로 값에 문제 발생 시 할당도 되지 않기 때문이다.
//    final 키워드도 붙일 수 있다!
//    초기화 생성자를 사용하면 해당 객체(필드)에 final을 사용할 수 있다. 즉, 다른 곳에서 변형 불가능.
//    의존성 주입이 되지 않으면 객체가 생성되지 않으므로 NPE(NullPointerException)를 방어할 수 있다.
//
//    public Coding(Computer computer) {
//        this.computer = computer;
//    }

    private final int data = 10;
    private final Computer computer; // 여기에 직접 필드 주입을 하는 게 아니라, 이것의 생성자를 만들어 주는 것이다.
}












