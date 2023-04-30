package com.shop.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class}) // Auditing을 적용하기 위해 EntityListeners 어노테이션을 추가함
@MappedSuperclass // 공통 매핑 정보가 필요할 때 사용하는 어노테이션. 이걸 적어두면 이게 공통 멤버변수 매핑을 위한 클래스임을 뜻함.
@Data
public class BaseTimeEntity {
    
    @CreatedDate // 엔티티가 생성되어 저장될 때 시간을 자동으로 저장함
    @Column(updatable = false) // 이 멤버변수는 업데이트 불가
    private LocalDateTime regTime; // 등록 시간

    @LastModifiedDate // 엔티티의 값이 변경될 때 시간을 자동으로 저장함
    private LocalDateTime updateTime; // 수정 시간
}
