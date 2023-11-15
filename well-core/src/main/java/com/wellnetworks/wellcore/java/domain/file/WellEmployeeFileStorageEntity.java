package com.wellnetworks.wellcore.java.domain.file;
//사원 파일
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자를 생성하되 접근 레벨 protected
@EntityListeners(AuditingEntityListener.class) //되기 전에 자동으로 생성 시간 및 수정 시간을 업데이트하는 등의 작업을 수행할 수 있다
public class WellEmployeeFileStorageEntity {
    @Id //파일_idx
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키 지정
    @Column(name = "employee_file_id")
    private Long id;

    @Column(name = "em_idx")
    private String employeeIdx;

    @OneToOne
    @JoinColumn(name = "file_id")
    private WellFileStorageEntity file;


    @Builder
    public WellEmployeeFileStorageEntity(String employeeIdx, WellFileStorageEntity file){
        this.employeeIdx = employeeIdx;
        this.file = file;
    }
}
