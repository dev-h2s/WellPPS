package com.wellnetworks.wellwebapi.java.controller.member;

import com.wellnetworks.wellcore.java.dto.member.ChangePasswordRequest;
import com.wellnetworks.wellsecure.java.config.CookieUtil;
import com.wellnetworks.wellsecure.java.jwt.TokenProvider;
import com.wellnetworks.wellsecure.java.request.ApiResponse;
import com.wellnetworks.wellsecure.java.request.TokenResponse;
import com.wellnetworks.wellsecure.java.request.UserLoginReq;
import com.wellnetworks.wellsecure.java.service.EmployeeUserDetails;
import com.wellnetworks.wellsecure.java.service.PartnerUserDetails;
import com.wellnetworks.wellsecure.java.service.RefreshTokenService;
import com.wellnetworks.wellsecure.java.service.WellUserDetailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final RefreshTokenService refreshTokenService;
    private final TokenProvider tokenProvider;
    private final WellUserDetailService detailService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping(value = "/init/login")
    public ResponseEntity<ApiResponse> login(@RequestBody UserLoginReq loginReq) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword());

            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 인증에 성공하면 JWT 토큰을 생성
            String accessToken = tokenProvider.createToken(authentication);
            String refreshToken = tokenProvider.createRefreshToken(authentication);

            // UserDetails 객체를 조회하여 첫 로그인 여부를 확인
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            boolean isFirstLogin = false;

            // 첫 로그인 여부 확인
            if (userDetails instanceof EmployeeUserDetails) {
                isFirstLogin = ((EmployeeUserDetails) userDetails).getEmployee().getIsFirstLogin();
            } else if (userDetails instanceof PartnerUserDetails) {
                isFirstLogin = ((PartnerUserDetails) userDetails).getPartner().getIsFirstLogin();
            }
            // 응답 객체를 생성
            ApiResponse response = isFirstLogin
                    ? new ApiResponse("첫 로그인시. 패스워드를 변경해주세요.", new TokenResponse(accessToken, refreshToken))
                    : new ApiResponse("로그인 성공", new TokenResponse(accessToken, refreshToken));

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

            detailService.changePassword(changePasswordRequest); // 서비스를 호출하여 비밀번호 변경 처리
            // 비밀번호 변경 성공 응답 반환
            return ResponseEntity.ok(new ApiResponse("비밀번호가 성공적으로 변경되었습니다.", null));

    }

    // 로그아웃
    @PostMapping("/init/logout")
    public ResponseEntity<?> logoutUser(HttpServletRequest request, HttpServletResponse response) {        // 토큰 추출 (예: 헤더, 쿠키 등에서)
        String refreshToken = CookieUtil.extractToken(request);
        System.out.println(refreshToken);
//        // 리프레시 토큰 무효화
//        refreshTokenService.deleteRefreshToken();
//        CookieUtil.deleteCookie(request, response, "access_token");
//        // 성공적인 로그아웃 응답 반환
//        return ResponseEntity.ok().body("User logged out successfully");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            refreshTokenService.deleteRefreshToken(userDetails);
            CookieUtil.deleteCookie(request, response, "access_token");

            return ResponseEntity.ok().body("User logged out successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No authenticated user found.");
        }
    }
}