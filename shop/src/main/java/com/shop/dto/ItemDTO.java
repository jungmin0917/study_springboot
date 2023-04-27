package com.shop.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ItemDTO {
    private Long id;
    private String itemNm;
    private int price;
    private String itemDetail;
    private String sellStatCd;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;
}
