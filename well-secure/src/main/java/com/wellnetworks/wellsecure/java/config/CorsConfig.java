//package com.wellnetworks.wellsecure.java.config;
//
//
//
//import org.springframework.web.filter.CorsFilter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//@Configuration
//public class CorsConfig {
//
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true); // 쿠키 전달을 허용
//        config.addAllowedOrigin("*"); // 모든 오리진을 허용
////        config.addAllowedOrigin("http://weldev.iptime.org:8888"); // 클라이언트 도메인 추가
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("GET");
//        config.addAllowedMethod("POST");
//        config.addAllowedMethod("PUT");
//        config.addAllowedMethod("DELETE");
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter((CorsConfigurationSource) source);
//    }
//}
