package com.wellnetworks.wellwebapi.java.controller.member;

import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeUserEntity;

import com.wellnetworks.wellcore.java.repository.member.employee.WellEmployeeUserRepository;
import com.wellnetworks.wellcore.java.service.member.TokenResponse;

import com.wellnetworks.wellsecure.java.jwt.TokenProvider;
import com.wellnetworks.wellsecure.java.request.UserLoginReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RequestMapping("/admin/hr/")
@RestController
@ComponentScan(basePackages={"com.wellnetworks.wellcore","com.wellnetworks.wellsecure"})
public class EmployeeUserController {

    @Autowired
    private WellEmployeeUserRepository employeeUserRepository;

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    @Autowired
    public EmployeeUserController(AuthenticationManager authenticationManager, TokenProvider tokenProvider ) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
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

            // UserDetails 객체를 조회하여 첫 로그인 여부를 확인
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            WellEmployeeUserEntity userEntity = employeeUserRepository.findByEmployeeIdentification(userDetails.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

            // 응답 객체를 생성
            ApiResponse response;
            if (userEntity.getIsFirstLogin()) {
                // isFirstLogin 상태를 업데이트
//                userEntity.markFirstLoginComplete(); // 이 메서드는 상태를 변경하는 로직을 수행합니다.
                employeeUserRepository.save(userEntity);
                // 클라이언트에게 패스워드 변경을 요청하는 응답을 보내기
                response = new ApiResponse("첫 로그인시. 패스워드를 변경해주세요.", new TokenResponse(accessToken, refreshToken));
            } else {
                // 첫 로그인이 아니면 정상적인 로그인 응답을 보낸다
                response = new ApiResponse("로그인 성공", new TokenResponse(accessToken, refreshToken));
            }

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
        }
        }

//        @PostMapping(value = "employee/update_pwd")
//        public ResponseEntity<ApiResponse> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
//            try {
//                // 사용자를 찾고, 패스워드 변경 로직 수행 ...
//                // 패스워드 변경이 성공하면 isFirstLogin 상태 업데이트
//                WellEmployeeUserEntity userEntity = employeeUserRepository.findByEmployeeIdentification(changePasswordRequest.getUsername())
//                        .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
//
//                if (userEntity.getIsFirstLogin()) {
//                    userEntity.markFirstLoginComplete();
//                    employeeUserRepository.save(userEntity);
//                }
//
//                // 응답 반환
//                return ResponseEntity.ok(new ApiResponse("패스워드 변경 성공", null));
//            } catch (Exception e) {
//                // 에러 처리
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("패스워드 변경 중 오류가 발생했습니다.", null));
//            }

    }
