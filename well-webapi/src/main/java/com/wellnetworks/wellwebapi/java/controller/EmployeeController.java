package com.wellnetworks.wellwebapi.java.controller;

import com.wellnetworks.wellcore.java.dto.member.WellEmployeeInfoDTO;
import com.wellnetworks.wellcore.java.dto.member.WellEmployeeInfoDetailDTO;
import com.wellnetworks.wellcore.java.dto.member.WellEmployeeJoinDTO;
import com.wellnetworks.wellcore.java.service.member.WellEmployeeService;
import com.wellnetworks.wellsecure.java.request.ApiResponse;
import com.wellnetworks.wellcore.java.dto.member.ChangePasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequestMapping("/admin/hr/")
@RestController
@ComponentScan(basePackages={"com.wellnetworks.wellcore","com.wellnetworks.wellsecure"})
public class EmployeeController {

    @Autowired
    private WellEmployeeService wellEmployeeService;


    @Autowired
    public EmployeeController(WellEmployeeService wellEmployeeService) {
        this.wellEmployeeService = wellEmployeeService;
    }

    // 사원 하나 조회
    @GetMapping("employee/{employeeIdx}")
    public Optional<WellEmployeeInfoDetailDTO> getEmployee(@PathVariable String employeeIdx) throws ClassNotFoundException {
        Optional<WellEmployeeInfoDetailDTO> wellEmployeeInfoDetailDTO = wellEmployeeService.getEmployeeByEmployeeIdx(employeeIdx);
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

    //사원 생성
    @PostMapping(value = "employee/signUp")
    public ResponseEntity<String> createEmployeeUser(WellEmployeeJoinDTO createDTO) throws Exception {
//        try {
            String tempPassword = wellEmployeeService.employeeJoin(createDTO);
            // 콘솔에 임시 비밀번호 출력
            System.out.println("생성된 임시 비밀번호: " + tempPassword);
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다. 임시 비밀번호: " + tempPassword);
//        } catch (Exception e) {
//            // 예외 처리
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 중 오류가 발생하였습니다.");
//        }
    }

    //패스워드 변경 : 유저에서 사용
//    @PostMapping(value = "employee/updatePwd")
//    public ResponseEntity<ApiResponse> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
//        try {
//            wellEmployeeService.changePassword(changePasswordRequest);
//            return ResponseEntity.ok(new ApiResponse("패스워드가 변경되었습니다", null));
//        } catch (IllegalArgumentException ex) {
//            return ResponseEntity.badRequest().body(new ApiResponse(ex.getMessage(), null));
//        } catch (UsernameNotFoundException ex) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("User not found.", null));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("An error occurred while changing the password.", null));
//        }
//    }



    // 검색
    @GetMapping("employee/search")
    public List<WellEmployeeInfoDTO> searchEmployeeList(
            @RequestParam(value = "belong", required = false) String belong,
            @RequestParam(value = "employmentState", required = false) String employmentState,
            @RequestParam(value = "employeeName", required = false) String employeeName,
            @RequestParam(value = "employeeIdentification", required = false) String employeeIdentification,
            @RequestParam(value = "position", required = false) String position,
            @RequestParam(value = "telPrivate", required = false) String telPrivate,
            @RequestParam(value = "department", required = false) String department
    ) {
        // 서비스 레이어의 검색 메서드 호출
        return wellEmployeeService.searchEmployeeList(
                belong,
                employmentState,
                employeeName,
                employeeIdentification,
                position,
                telPrivate,
                department
    );
    }




}
