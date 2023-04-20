package com.example.ex00.dependency;

import lombok.Data;

@Data
public class Coding {
//    아래와 같이 쓰게 되면 코딩이 컴퓨터에게 "의존"하기 때문에 좋지 않다.
//    Computer computer = new Computer();

    private int ram;

/*
    // 기본 생성자
    public Coding() {
    }

    // getter, setter
    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    // toString 오버라이드
    @Override
    public String toString() {
        return "Coding{" +
                "ram=" + ram +
                '}';
    }*/
}
