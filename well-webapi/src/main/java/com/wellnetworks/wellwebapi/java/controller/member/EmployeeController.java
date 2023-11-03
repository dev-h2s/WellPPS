package com.wellnetworks.wellwebapi.java.controller.member;

import com.wellnetworks.wellcore.java.dto.member.WellEmployeeInfoDTO;
import com.wellnetworks.wellcore.java.dto.member.WellEmployeeInfoDetailDTO;
import com.wellnetworks.wellcore.java.dto.member.WellEmployeeJoinDTO;
import com.wellnetworks.wellcore.java.service.member.TokenResponse;
import com.wellnetworks.wellcore.java.service.member.WellEmployeeService;

import com.wellnetworks.wellsecure.java.jwt.TokenProvider;
import com.wellnetworks.wellsecure.java.request.UserLoginReq;
import com.wellnetworks.wellsecure.java.service.WellUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
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
    private WellUserDetailService detailService;

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    @Autowired
    public  EmployeeController(AuthenticationManager authenticationManager, TokenProvider tokenProvider, WellEmployeeService wellEmployeeService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.wellEmployeeService = wellEmployeeService;
    }

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

    //사원 생성
    @PostMapping(value = "employee/signUp")
    public ResponseEntity<String> createEmployeeUser(WellEmployeeJoinDTO createDTO) throws Exception {
        try {
            String tempPassword = wellEmployeeService.employeeJoin(createDTO);
            // 콘솔에 임시 비밀번호 출력
            System.out.println("생성된 임시 비밀번호: " + tempPassword);
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다. 임시 비밀번호: " + tempPassword);
        } catch (Exception e) {
            // 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 중 오류가 발생하였습니다.");
        }
    }


    //로그인
    @PostMapping(value = "employee/login")
    public ResponseEntity<ApiResponse> login(@RequestBody UserLoginReq loginReq) {


        try {
            // 사용자의 인증 정보를 검증
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword())
            );
            // 인증에 성공하면 JWT 토큰을 생성
            String accessToken = tokenProvider.createToken(authentication);
            String refreshToken = tokenProvider.createRefreshToken(authentication);

            TokenResponse tokenResponse = new TokenResponse();
            tokenResponse.setAccessToken(accessToken);
            tokenResponse.setRefreshToken(refreshToken);
            ApiResponse response = new ApiResponse("로그인 성공", tokenResponse);
            // JWT 토큰을 클라이언트에게 응답으로 반환
            return ResponseEntity.ok(response);


        } catch (BadCredentialsException e) {
            System.out.println("username은" + loginReq.getUsername() + "password는" + loginReq.getPassword());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("올바르지 않은 사용자 이름 또는 비밀번호입니다.", null));
        } catch (LockedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("계정이 잠겼습니다. 관리자에게 문의하세요.", null));
        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("계정이 비활성화되었습니다.", null));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("인증 중 오류가 발생하였습니다.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("서버 내부 오류가 발생하였습니다.", null));
        }

    }
}
