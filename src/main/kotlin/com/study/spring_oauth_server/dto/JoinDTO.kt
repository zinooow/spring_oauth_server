package com.study.spring_oauth_server.dto

data class JoinDTO(
    val username: String,
    val password: String,
    val nickname: String,
    val phone: String,
) {
}