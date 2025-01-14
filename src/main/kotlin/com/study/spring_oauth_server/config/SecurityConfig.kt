package com.study.spring_oauth_server.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun filterChain(http:HttpSecurity): SecurityFilterChain {

        http
            .authorizeHttpRequests { auth -> auth
                .anyRequest().permitAll()
            }
            .csrf { csrf -> csrf.disable() }
            .formLogin{}
            .headers { headers -> headers.frameOptions{option -> option.sameOrigin()}}
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}