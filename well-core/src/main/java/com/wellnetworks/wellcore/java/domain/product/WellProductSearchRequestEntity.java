package com.wellnetworks.wellcore.java.domain.product;
//요금제 조회 요청
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "product_search_request_tb")
public class WellProductSearchRequestEntity extends WellProductSearchEntity {

// 요금제 조회 테이블의 pk이자 fk
// 조회 요청하는 apikey
// 조회할 때 요청되는 apikey

    @Column(name = "search_request_date_apikey") // 거래처 테이블과 연결되는 fk
    private String searchRequestDateApikey;

    @Column(name = "search_request_tel") // 요금제 정보 테이블의 fk
    private String searchRequestTel;


}
