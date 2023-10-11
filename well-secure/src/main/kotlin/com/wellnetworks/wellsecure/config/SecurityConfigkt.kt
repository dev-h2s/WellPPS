package com.wellnetworks.wellsecure.config

import com.wellnetworks.wellsecure.jwt.JwtAuthenticationFilterkt
import com.wellnetworks.wellsecure.jwt.JwtAuthorizationFilterkt
import com.wellnetworks.wellsecure.jwt.TokenProviderkt
import com.wellnetworks.wellsecure.service.AppAuthenticationManagerkt
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
//회원가입 관련 security 로직
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfigkt(
    val securityProperties: SecurityPropertieskt,
    val authenticationManager: AppAuthenticationManagerkt,
    val tokenProvider: TokenProviderkt
) {
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        http
            //.cors().configurationSource(corsConfigurationSource())
            .cors().and()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
//            .formLogin().disable()
//            .httpBasic().disable()
            //.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
            .addFilter(JwtAuthenticationFilterkt(authenticationManager, securityProperties, tokenProvider))
            .addFilter(JwtAuthorizationFilterkt(authenticationManager, securityProperties, tokenProvider))
//            .and()
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID", "remember-me")

        http.authorizeHttpRequests()
            .requestMatchers("/init/**",).permitAll()
            .requestMatchers("/api/**").permitAll()
            .requestMatchers("/", "/**", "/init/**").permitAll()
            .requestMatchers("/admin/hr/business/**").permitAll()
            .requestMatchers("/admin/hr/partner/**").permitAll()
            .requestMatchers("/file/**").permitAll()
            .requestMatchers("/login").permitAll()
            .requestMatchers("/logout").permitAll()
            .requestMatchers("/signup").permitAll()
            .requestMatchers("/**").permitAll() //그외 나머지 요청은 누구나 접근 가능
            .anyRequest().authenticated()

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource = UrlBasedCorsConfigurationSource().also {
        cors -> CorsConfiguration().apply {
            allowedOrigins = listOf("*", "*/*")
            //allowedOrigins = listOf("http://welldev.iptime.org:8888")
            allowedMethods = listOf("POST", "PUT", "DELETE", "GET", "OPTIONS", "HEAD")
            allowedHeaders = listOf("*"/*
                "Authorization",
                "Context-Type",
                "X-Requested-With",
                "Accept",
                "Origin",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers",
                "Content-Type",
                "Access-Control-Request-Credentials",
                "Access-Control-Request-Private-Network"
*/
            )
            exposedHeaders = listOf(
                "Access-Control-Allow-Origin",
                "Access-Control-Allow-Credentials",
                "Authorization",
                "Content-Disposition",
                //"Access-Control-Allow-Private-Network"
            )
            maxAge = 3600
            cors.registerCorsConfiguration("/**", this)
        }
    }

}