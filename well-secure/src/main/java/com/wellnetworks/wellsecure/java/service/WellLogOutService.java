package com.wellnetworks.wellsecure.java.service;


import com.wellnetworks.wellsecure.java.config.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class WellLogOutService {

    private final RefreshTokenService refreshTokenService;
    private final WellUserDetailService userDetailService; // 이 부분을 추가


    @Autowired
    public WellLogOutService(RefreshTokenService refreshTokenService, WellUserDetailService userDetailService) {
        this.refreshTokenService = refreshTokenService;
        this.userDetailService = userDetailService; // 생성자에서 주입
    }


    // 로그아웃 시 호출될 메서드
    @Transactional
    public void logoutAndRemoveRefreshToken(String username, HttpServletRequest request, HttpServletResponse response) {
        UserDetails userDetails = userDetailService.loadUserByUsername(username);
//        db에서 리프레쉬 토큰을 삭제
        refreshTokenService.deleteRefreshToken(userDetails);

        // 쿠키에서 액세스 토큰을 삭제
//        CookieUtil.deleteCookie(request, response, "access_token");
    }
}
