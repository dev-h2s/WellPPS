package com.wellnetworks.wellcore.java.domain.permission.department;

import com.wellnetworks.wellcore.java.domain.permission.personal.WellPersonalDropDownEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Table(name = "department_menu_permission_tb")
public class WellDepartmentMenuPermissionEntity {

//    부서 권한의 dropdown content의 각 권한 여부를 지정하기 위한 pk
    @Id
    @Column(name = "dep_menu_id") //거래처_id
    private Long depMenuId;
    //    부서 drop의 하위 컨텐트의 FK로 받는 id
    @ManyToOne
    @JoinColumn(name = "dep_drop_content_id", insertable = false, updatable = false)
    private WellDepartmentDropDownContentEntity dropdownContent;

    //    부서 입력 권한의 허용 여부
    @Column(name = "input_flag")
    private Boolean inputFlag;

    //    부서 조회 권한의 허용 여부
    @Column(name = "view_flag")
    private Boolean viewFlag;

    //    부서 수정 권한의 허용 여부
    @Column(name = "edit_flag")
    private Boolean editFlag;

    //    부서 삭제 권한의 허용 여부
    @Column(name = "delete_flag")
    private Boolean deleteFlag;

    //    부서 검색 권한의 허용 여부
    @Column(name = "search_flag")
    private Boolean searchFlag;

    //    부서 메뉴 권한의 허용 여부
    @Column(name = "excel_flag")
    private Boolean excelFlag;


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
