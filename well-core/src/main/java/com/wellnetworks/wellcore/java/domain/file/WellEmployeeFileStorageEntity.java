package com.wellnetworks.wellcore.java.domain.file;
//사원 파일
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeEntity;
import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class WellEmployeeFileStorageEntity {
    @Id //파일_idx
    @Column(name = "file_idx")
    private String fileIdx;

    @Column(name = "em_idx")
    private String employeeIdx;

    @ManyToOne(fetch = LAZY) //사원_idx
    @JoinColumn(name = "em_idx", insertable = false, updatable = false)
    private WellEmployeeFileStorageEntity Employee;

    @OneToOne(fetch = LAZY) // 사원파일과 첨부파일 간의 연결
    @JoinColumn(name = "file_idx", insertable = false, updatable = false)
    private WellFileStorageEntity file;

    protected WellEmployeeFileStorageEntity(){}

    public WellEmployeeFileStorageEntity(WellFileStorageEntity file, WellEmployeeEntity employee){
        this.fileIdx = file.getFileIdx();
        this.employeeIdx = employee.getEmployeeIdx();
    }
}
