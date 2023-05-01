package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import com.shop.dto.ItemFormDTO;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

// 책에서는 Getter, Setter, ToString 어노테이션 따로 사용했는데 귀찮으므로 그냥 Data 썼음
@Entity
@Table(name="item") // 엔티티와 매핑할 DB 테이블을 지정함. 이렇게 하면 DB에 있는 item이라는 테이블과 매핑할 것임
@Data
public class Item extends BaseEntity{

    // Entity 클래스는 반드시 기본키를 가져야 하므로 상품 ID를 기본키로 설정함
    @Id // 상품 ID를 기본값으로 쓸 것임을 명시함
    @Column(name = "item_id") // 해당 테이블의 item_id 컬럼과 매핑할 것임을 명시함
    @GeneratedValue(strategy = GenerationType.AUTO) // 기본키 생성 전략을 JPA에게 위임함
    private Long id; // 상품 ID

    @Column(nullable = false, length = 50) // name 속성을 생략하면 알아서 클래스의 필드명과 같도록 매핑함. 여기서 주의점은 클래스 필드명을 카멜 케이스(itemNm)으로 하였더라도 매핑은 스네이크 케이스(item_nm)으로 됨.
    private String itemNm; // 상품 이름

    @Column(name="price", nullable = false)
    private Integer price; // 상품 가격

    @Column(nullable = false) // nullable : 생략 가능 여부
    private Integer stockNumber; // 상품 재고

    @Lob // 사이즈가 큰 데이터라면 @Lob 어노테이션을 붙여준다
    @Column(nullable = false)
    private String itemDetail; // 상품 상세 설명

    @Enumerated(EnumType.STRING) // 엔티티에서는 ENUM 객체 타입이지만 저장은 VARCHAR로 저장된다.
    private ItemSellStatus itemSellStatus; // 판매 상태 (enum 클래스)

    // 아래 두 개는 @Column 어노테이션 생략 (알아서 매핑됨)
    
//    공통으로 상속받았음
//    private LocalDateTime regTime; // 상품 등록 시간
//    private LocalDateTime updateTime; // 상품 수정 시간

    public void updateItem(ItemFormDTO itemFormDTO){
        this.itemNm = itemFormDTO.getItemNm();
        this.price = itemFormDTO.getPrice();
        this.stockNumber = itemFormDTO.getStockNumber();
        this.itemDetail = itemFormDTO.getItemDetail();
        this.itemSellStatus = itemFormDTO.getItemSellStatus();
    }
}










