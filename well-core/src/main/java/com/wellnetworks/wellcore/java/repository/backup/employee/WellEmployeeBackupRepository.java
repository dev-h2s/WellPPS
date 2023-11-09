package com.wellnetworks.wellcore.java.repository.backup.employee;

import com.wellnetworks.wellcore.java.domain.backup.employee.WellEmployeeEntityBackup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WellEmployeeBackupRepository  extends JpaRepository<WellEmployeeEntityBackup, String> {
    //사원_idx 검색
    WellEmployeeEntityBackup findByEmployeeIdx(String employeeIdx);

    //사원 등록
    WellEmployeeEntityBackup save(WellEmployeeEntityBackup wellEmployeeEntityBackup);

    //사원_idx삭제(체크항목 삭제)
    WellEmployeeEntityBackup deleteByEmployeeIdx(String employeeIdx);
    
}
