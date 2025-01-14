package com.study.spring_oauth_server.service

import com.study.spring_oauth_server.dto.JoinDTO
import com.study.spring_oauth_server.entity.UserEntity
import com.study.spring_oauth_server.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService (
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
){
    fun join(userDTO: JoinDTO): UserEntity {
        val userEntity = UserEntity(
            username = userDTO.username,
            password = passwordEncoder.encode(userDTO.password),
            role = "ADMIN",
            nickname = userDTO.nickname,
            phone = userDTO.phone,
        )
        userRepository.save(userEntity)
        return userEntity
    }
}