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
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    val securityProperties: SecurityProperties,
    val authenticationManager: AppAuthenticationManager,
    val tokenProvider: TokenProvider
) {
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        http
            .cors().and()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
//            .formLogin().disable()
//            .httpBasic().disable()
            .authorizeRequests()
            .antMatchers("/api/**").permitAll()
            .antMatchers("/", "/**", "/init/**").permitAll()
            .antMatchers("/admin/hr/business/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilter(JwtAuthenticationFilter(authenticationManager, securityProperties, tokenProvider))
            .addFilter(JwtAuthorizationFilter(authenticationManager, securityProperties, tokenProvider))
//            .and()
            .logout()
            .logoutUrl("/logout")
            .deleteCookies("JSESSIONID", "remember-me")
        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource = UrlBasedCorsConfigurationSource().also {
        cors -> CorsConfiguration().apply {
            allowedOrigins = listOf("*")
            allowedMethods = listOf("POST", "PUT", "DELETE", "GET", "OPTIONS", "HEAD")
            allowedHeaders = listOf(
                "Authorization",
                "Context-Type",
                "X-Requested-With",
                "Accept",
                "Origin",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers"
            )
            exposedHeaders = listOf(
                "Access-Control-Allow-Origin",
                "Access-Control-Allow-Credentials",
                "Authorization",
                "Content-Disposition"
            )
            maxAge = 3600
            cors.registerCorsConfiguration("/**", this)
        }
    }
}