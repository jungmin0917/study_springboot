package com.shop.entity;

import com.shop.constant.Role;
import com.shop.dto.MemberFormDTO;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name="member")
@Data
public class Member {

    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.AUTO) // 기본키 생성 전략
    private Long id; // PK 지정

    private String name;

    @Column(unique = true) // 컬럼에 unique 속성 지정
    private String email;

    private String password;
    private String address;

    @Enumerated(EnumType.STRING) // 자바의 enum 타입을 엔티티의 속성으로 지정함. Enum을 사용할 때 기본적으로 순서가 저장되는데, enum의 순서가 바뀔 경우 문제가 발생할 수 있기에 EnumType.STRING 옵션을 사용하여 엔티티에 String으로 저장한다.
    private Role role;

    // Member 엔티티를 생성하는 메소드이다.
    // Member 엔티티에 회원을 생성하는 메소드를 만들어서 관리를 한다면 코드가 변경되더라도 한 군데만 수정하면 되는 이점이 있다. 여기서는 중간에 메소드를 거쳐서 비밀번호를 암호화하여 엔티티를 만들기 위함도 있다.
    public static Member createMember(MemberFormDTO memberFormDTO, PasswordEncoder passwordEncoder){
        Member member = new Member();

        member.setName(memberFormDTO.getName());
        member.setEmail(memberFormDTO.getEmail());

        // setPassword를 하기 전에 비밀번호를 암호화한다
        String password = passwordEncoder.encode(memberFormDTO.getPassword());
        member.setPassword(password);
        member.setAddress(memberFormDTO.getAddress());
        member.setRole(Role.USER); // 일반 회원의 권한을 준다

        return member;
    }

}
