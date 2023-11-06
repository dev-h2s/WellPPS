package com.wellnetworks.wellcore.java.dto.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {
    private String username;// 사용자의 아이디
    private String currentPassword; // 현재 비밀번호 혹은 임시 비밀번호
    private String newPassword;// 사용자가 입력한 새로운 패스워드
    private String confirmPassword;// 새로운 패스워드를 재확인하기 위한 필드


    public ChangePasswordRequest(String username, String newPassword, String confirmPassword, String currentPassword) {
        this.username = username;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }
}
