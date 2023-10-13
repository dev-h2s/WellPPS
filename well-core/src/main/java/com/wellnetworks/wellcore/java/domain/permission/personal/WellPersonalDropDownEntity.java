package com.wellnetworks.wellcore.java.domain.permission.personal;
// 개인 dropdown
import com.wellnetworks.wellcore.java.domain.charge.WellChargeHistoryEntity;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeEntity;
import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerUserEntity;
import com.wellnetworks.wellcore.java.domain.product.WellProductSearchEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Table(name = "personal_dropdown_tb")
public class WellPersonalDropDownEntity {
    // 개인 권한의 dropdown의 각 권한 여부를 지정하기 위한 pk
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "per_drop_id")
    private Long perDropId;

    // 직원 테이블에서 받는 fk 단방향
    @OneToOne
    @JoinColumn(name = "em_id", insertable = false, updatable = false)
    private WellEmployeeEntity employee;

    // 개인 dropdown의 이름
    @Column(name = "per_drop_name")
    private String dropName;

    public static Map<String, String> asMap() {
        // 결과를 저장할 빈 Map을 생성
        Map<String, String> resultMap = new HashMap<>();
        // 클래스에 선언된 모든 필드를 가져옴
        Field[] fields = WellPersonalDropDownEntity.class.getDeclaredFields();

        // 가져온 모든 필드에 대한 반복문
        for (Field field : fields) {
            try {
                // 필드의 접근성을 설정하여 private 필드에 접근할 수 있게 함
                field.setAccessible(true);
                // 필드의 이름을 키로, 해당 필드의 값을 문자열로 변환하여 Map에 추가
                resultMap.put(field.getName(), String.valueOf(field.get(null)));
            } catch (IllegalAccessException e) {
                // 필드에 접근할 수 없는 경우 예외가 발생할 수 있으며, 이 경우 예외를 처리
                e.printStackTrace();
            }
        }
        // 변환된 결과 Map을 반환
        return resultMap;
    }
}

