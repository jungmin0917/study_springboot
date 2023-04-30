package com.shop.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Data
public class BaseEntity extends BaseTimeEntity{

    @CreatedBy
    @Column(updatable = false) // 수정 불가능하게 함
    private String createdBy; // 생성될 때 입력됨

    @LastModifiedBy
    private String modifiedBy; // 이것도 수정하면 바뀌는 듯

}
