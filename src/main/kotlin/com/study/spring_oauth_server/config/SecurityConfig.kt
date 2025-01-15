package com.study.spring_oauth_server.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.MediaType
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher

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
    @Order(Ordered.HIGHEST_PRECEDENCE)
    fun authorizationServer(http:HttpSecurity): SecurityFilterChain {
        val authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer.authorizationServer().oidc(Customizer.withDefaults());
        http
            .securityMatcher(authorizationServerConfigurer.endpointsMatcher)
            .with(authorizationServerConfigurer, Customizer.withDefaults())
            .exceptionHandling { exceptions -> exceptions
                    .defaultAuthenticationEntryPointFor(
                        LoginUrlAuthenticationEntryPoint("/login"),
                        MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                    )
            }
        return http.build()
    }

    @Bean
    fun authorizationServerSettings(): AuthorizationServerSettings =
        AuthorizationServerSettings.builder()
            .issuer("http://localhost:9000")
            .authorizationEndpoint("/oauth2/v1/authorize")
            .tokenEndpoint("/oauth2/v1/token")
            .tokenIntrospectionEndpoint("/oauth2/v1/introspect") // 토큰 상태
            .tokenRevocationEndpoint("/oauth2/v1/revoke") // 토큰 폐기 RFC 7009
            .jwkSetEndpoint("/oauth2/v1/jwks") // 공개키 확인
            .oidcLogoutEndpoint("/connect/v1/logout")
            .oidcUserInfoEndpoint("/connect/v1/userinfo") // 리소스 서버 유저 정보 연관
            .oidcClientRegistrationEndpoint("/connect/v1/register") // OAuth2 사용 신청
            .build()

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

}