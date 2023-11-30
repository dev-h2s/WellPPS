//package com.wellnetworks.wellsecure.java.service;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.core.Authentication;
//
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.logout.LogoutHandler;
//import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//@Component
//public class CustomLogoutHandler implements LogoutHandler {
//
//    private final RefreshTokenService refreshTokenService;
//    public CustomLogoutHandler(RefreshTokenService refreshTokenService){
//        this.refreshTokenService = refreshTokenService;
//    }
//
//    @Transactional
//    @Override
//    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
//        // 로그아웃 시 UserDetails를 가져옵니다.
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//
//        // 리프레시 토큰 삭제
//        logoutDeleteRefreshToken(userDetails);
//    }
//
//
//    @Transactional
//    public void logoutDeleteRefreshToken(UserDetails userDetails) {
//        if (userDetails instanceof EmployeeUserDetails) {
//            refreshTokenService.deleteRefreshToken(userDetails);
//        } else if (userDetails instanceof PartnerUserDetails) {
//            refreshTokenService.deleteRefreshToken(userDetails);
//        }
//    }
//}
