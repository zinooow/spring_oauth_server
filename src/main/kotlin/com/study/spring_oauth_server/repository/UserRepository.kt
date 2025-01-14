package com.study.spring_oauth_server.repository

import com.study.spring_oauth_server.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository:JpaRepository<UserEntity, Long> {
    fun findByUsername(username: String): UserEntity?
}