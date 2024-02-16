package com.wellnetworks.wellcore.java.domain.code;
//코드관리

import com.wellnetworks.wellcore.java.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Builder(toBuilder = true)
public class WellCodeEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;

    @Column(name = "c_type") //관리 구분
    private String cType;

    @Column(name = "name") //코드명
    private String name;

    @Column(name = "sort") //정렬순서
    private Long sort;
}
