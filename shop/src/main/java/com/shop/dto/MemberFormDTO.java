package com.shop.dto;

import lombok.Data;

@Data
public class MemberFormDTO {
    private String name;
    private String email;
    private String password;
    private String address;
}
