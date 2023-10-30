package com.wellnetworks.wellwebapi.java.controller.member;

import com.wellnetworks.secure.java.config.SecurityConfig;
import com.wellnetworks.wellcore.java.dto.member.WellEmployeeInfoDTO;
import com.wellnetworks.wellcore.java.dto.member.WellEmployeeInfoDetailDTO;
import com.wellnetworks.wellcore.java.service.member.WellEmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@RequestMapping(("/admin/hs/"))
@RestController
@ComponentScan(basePackages={"com.wellnetworks.wellcore","com.wellnetworks.secure"})
public class EmployeeController {
    @Autowired
    private WellEmployeeService wellEmployeeService;

    @Autowired
    private SecurityConfig securityConfig;

    // 사원 하나 조회
    @GetMapping("employee/{employeeIdx}")
    public Optional<WellEmployeeInfoDetailDTO> getEmployee(@PathVariable String employeeIdx) throws ClassNotFoundException {
        Optional<WellEmployeeInfoDetailDTO> wellEmployeeInfoDetailDTO = wellEmployeeService.getemployeeByemployeeIdx(employeeIdx);
        if (wellEmployeeInfoDetailDTO == null) {
            throw new ClassNotFoundException(String.format("IDX[%s] not found", employeeIdx));
        }

        return wellEmployeeInfoDetailDTO;
    }

    //사원 리스트 조회
    @GetMapping("employee")
    public List<WellEmployeeInfoDTO> getEmployeeList(
            @RequestParam(required = false) String employeeIdx,
            @RequestParam(required = false) Integer employeeId,
            @RequestParam(required = false) String belong,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String discountCategory,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String position,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyyMMdd") Date employeeRegisterDate,
            @RequestParam(required = false) String telPrivate,
            @RequestParam(required = false) String eMail,
            @RequestParam(required = false) String employmentState
    ) {
        List<WellEmployeeInfoDTO> employeeList = wellEmployeeService.getAllemployees();

        return employeeList;
    }


}
