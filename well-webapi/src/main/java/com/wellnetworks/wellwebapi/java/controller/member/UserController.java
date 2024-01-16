package com.wellnetworks.wellwebapi.java.controller.member;

import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeUserEntity;
import com.wellnetworks.wellcore.java.dto.member.ChangePasswordRequest;
import com.wellnetworks.wellcore.java.repository.member.employee.WellEmployeeUserRepository;
import com.wellnetworks.wellsecure.java.config.CookieUtil;
import com.wellnetworks.wellsecure.java.request.ApiResponse;

import com.wellnetworks.wellsecure.java.jwt.TokenProvider;
import com.wellnetworks.wellsecure.java.request.TokenResponse;
import com.wellnetworks.wellsecure.java.request.UserLoginReq;
import com.wellnetworks.wellsecure.java.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
@ComponentScan(basePackages = {"com.wellnetworks.wellcore", "com.wellnetworks.wellsecure"})
@RequiredArgsConstructor

public class UserController {

    private final RefreshTokenService refreshTokenService;
    private final WellEmployeeUserRepository employeeUserRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final WellUserDetailService detailService;
    private final WellLogOutService logOutService;





    // 기존 로그인 로직
//    //로그인
    @PostMapping(value = "init/login")
    public ResponseEntity<ApiResponse> login(@RequestBody UserLoginReq loginReq) {
        WellEmployeeUserEntity userEntity = null;
        try {
            // 사용자의 인증 정보를 검증
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword())

            );
            // 인증에 성공하면 JWT 토큰을 생성
            String accessToken = tokenProvider.createToken(authentication);
            String refreshToken = tokenProvider.createRefreshToken(authentication);
            System.out.println("인증 객체: " + authentication);

            // UserDetails 객체를 조회하여 첫 로그인 여부를 확인
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            boolean isFirstLogin = false;

            // 첫 로그인 여부 확인
            if (userDetails instanceof EmployeeUserDetails) {
                isFirstLogin = ((EmployeeUserDetails) userDetails).isFirstLogin();
            } else if (userDetails instanceof PartnerUserDetails) {
                isFirstLogin = ((PartnerUserDetails) userDetails).isFirstLogin();
            }

            // 응답 객체를 생성
            ApiResponse response = isFirstLogin
                    ? new ApiResponse("첫 로그인시. 패스워드를 변경해주세요.", new TokenResponse(accessToken, refreshToken))
                    : new ApiResponse("로그인 성공", new TokenResponse(accessToken, refreshToken));

            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

//            executorService.scheduleAtFixedRate(() -> {
//                String newAccessToken = tokenProvider.createToken(authentication); // 새로운 토큰 생성 로직
//                System.out.println("New access token: " + newAccessToken);
//            }, 0, 10, TimeUnit.SECONDS);


            // JWT 토큰을 클라이언트에게 응답으로 반환
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException e) {
            // 사용자를 찾을 수 없을 때의 예외 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("없는 사용자입니다.", null));
        } catch (BadCredentialsException e) {
            System.out.println("username은" + loginReq.getUsername() + "password는" + loginReq.getPassword());
//            System.out.println(userEntity.getIsPasswordResetRequired());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("올바르지 않은 사용자 이름 또는 비밀번호입니다.", null));
        } catch (LockedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("계정이 잠겼습니다. 관리자에게 문의하세요.", null));
        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("계정이 비활성화되었습니다.", null));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("인증 중 오류가 발생하였습니다.", null));
        }
    }


    // 패스워드 변경
    @PostMapping(value = "/updatePwd")
    public ResponseEntity<ApiResponse> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        // 비밀번호 변경 시도
        try {
            detailService.changePassword(changePasswordRequest); // 서비스를 호출하여 비밀번호 변경 처리
            // 비밀번호 변경 성공 응답 반환
            return ResponseEntity.ok(new ApiResponse("비밀번호가 성공적으로 변경되었습니다.", null));
        } catch (IllegalArgumentException e) {
            // 비밀번호 불일치 등 잘못된 요청의 경우 400 응답
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(e.getMessage(), null));
        } catch (UsernameNotFoundException e) {
            // 사용자를 찾을 수 없는 경우 404 응답
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
//            // 다른 오류가 발생한 경우 500 응답
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("비밀번호 변경 중 오류가 발생했습니다.", null));
        }
    }

    // 로그아웃
    @PostMapping("/logoutCustom")
    public void logout(HttpServletRequest request, HttpServletResponse response) {

//
//        // 로그아웃 후 처리 (예: 성공 메시지 반환)
//        return ResponseEntity.ok("로그아웃 되었습니다.");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            System.out.println("Authentication 객체가 null입니다.");
            return;
        }

        Object principal = authentication.getPrincipal();
        UserDetails userDetails;


        if (principal instanceof String) {
            // Principal이 String 타입인 경우, UserDetailsService를 사용하여 UserDetails를 로드합니다.
            String username = (String) principal;
            userDetails = detailService.loadUserByUsername(username);
        } else if (principal instanceof UserDetails) {
            // Principal이 이미 UserDetails 타입인 경우, 직접 사용합니다.
            userDetails = (UserDetails) principal;
        } else {
            System.out.println("Principal 객체를 처리할 수 없습니다: " + principal.getClass());
            return;
        }
        System.out.println(userDetails);
        System.out.println(detailService.loadUserByUsername(userDetails.getUsername()));
        // UserDetails를 사용하여 로그아웃 로직 수행
//        logOutService.logoutAndRemoveRefreshToken();
//        refreshTokenService.deleteRefreshToken(userDetails);
        CookieUtil.deleteCookie(request, response, "access_token");
    }
}