package com.wellnetworks.wellwebapi.java.controller;

import com.wellnetworks.wellcore.java.dto.member.*;
import com.wellnetworks.wellcore.java.service.member.WellEmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    public ResponseEntity<Map<String, Object>> getEmployeeList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "employeeUserRegisterDate"));
        Page<WellEmployeeInfoDTO> employeePage = wellEmployeeService.getAllemployees(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("currentPage", employeePage.getNumber());
        response.put("items", employeePage.getContent());
        response.put("message", "");
        response.put("status", "OK");
        response.put("totalItems", employeePage.getTotalElements());
        response.put("totalPages", employeePage.getTotalPages());

        return ResponseEntity.ok(response);
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



    // 사원 검색
    @GetMapping("employee/search")
    public ResponseEntity<Map<String, Object>> searchEmployeeList(
            @RequestParam(required = false) String employeeIdx,
            @RequestParam(required = false) Integer employeeId,
            @RequestParam(value = "belong", required = false) String belong,
            @RequestParam(value = "employmentState", required = false) String employmentState,
            @RequestParam(value = "employeeName", required = false) String employeeName,
            @RequestParam(value = "employeeIdentification", required = false) String employeeIdentification,
            @RequestParam(value = "position", required = false) String position,
            @RequestParam(value = "telPrivate", required = false) String telPrivate,
            @RequestParam(value = "department", required = false) String department,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "employeeUserRegisterDate"));
        Page<WellEmployeeInfoDTO> employeePage = wellEmployeeService.searchEmployeeList(belong,employmentState,employeeName,employeeIdentification,position,telPrivate,department,pageable);
        // 서비스 레이어의 검색 메서드 호출

        Map<String, Object> response = new HashMap<>();
        response.put("currentPage", employeePage.getNumber());
        response.put("items", employeePage.getContent());
        response.put("message", "");
        response.put("status", "OK");
        response.put("totalItems", employeePage.getTotalElements());
        response.put("totalPages", employeePage.getTotalPages());

        return ResponseEntity.ok(response);

    }


    //사원 수정
    @PatchMapping("employee/update/{employeeIdx}")
    public ResponseEntity<String> UpdateEmployee(@Valid WellEmployeeUpdateDTO updateDTO,
                                               @PathVariable String employeeIdx) throws Exception {
        wellEmployeeService.update(employeeIdx, updateDTO);
        if (employeeIdx == null) {
            throw new ClassNotFoundException(String.format("IDX[%s] not found", employeeIdx));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("사원 정보가 성공적으로 수정되었습니다.");
    }

}
