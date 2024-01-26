package com.wellnetworks.wellcore.java.service.member;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class PasswordUtil {
    private static final String ALLOWED_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%^&*()";
    private static final int TEMP_PWD_LENGTH = 10;
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    public static String[] generateRandomPassword() {
        StringBuilder builder = new StringBuilder(TEMP_PWD_LENGTH);

        for (int i = 0; i < TEMP_PWD_LENGTH; i++) {
            builder.append(ALLOWED_STRING.charAt(RANDOM.nextInt(ALLOWED_STRING.length())));
        }

        String rawPassword = builder.toString();
        String encryptedPassword = ENCODER.encode(rawPassword); // 암호화된 비밀번호를 반환
        return new String[]{rawPassword, encryptedPassword};
    }
}