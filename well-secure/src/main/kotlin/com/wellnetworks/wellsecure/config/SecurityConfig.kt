package com.wellnetworks.wellsecure.config

import com.wellnetworks.wellsecure.jwt.JwtAuthenticationFilter
import com.wellnetworks.wellsecure.jwt.JwtAuthorizationFilter
import com.wellnetworks.wellsecure.jwt.TokenProvider
import com.wellnetworks.wellsecure.service.AppAuthenticationManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.CorsUtils
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    val securityProperties: SecurityProperties,
    val authenticationManager: AppAuthenticationManager,
    val tokenProvider: TokenProvider
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
            .addFilter(JwtAuthenticationFilter(authenticationManager, securityProperties, tokenProvider))
            .addFilter(JwtAuthorizationFilter(authenticationManager, securityProperties, tokenProvider))
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