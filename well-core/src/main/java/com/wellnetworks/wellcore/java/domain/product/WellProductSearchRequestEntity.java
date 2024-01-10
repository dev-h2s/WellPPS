package com.wellnetworks.wellcore.java.domain.product;
//요금제 조회 요청
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class WellProductSearchRequestEntity extends WellProductSearchEntity {

// 요금제 조회 테이블의 pk이자 fk

//    들어올 자리

    @Column(name = "search_request_date_apikey") // 조회 요청하는 apikey
    private String searchRequestDateApikey;

    @Column(name = "search_request_tel") // 조회할 때 요청되는 apikey
    private String searchRequestTel;


}
