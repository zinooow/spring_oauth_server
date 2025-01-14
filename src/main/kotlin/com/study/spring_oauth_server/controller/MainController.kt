package com.study.spring_oauth_server.controller

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MainController {

    @GetMapping("/")
    fun mainP():String {
        val name = SecurityContextHolder.getContext().authentication.name
        return "Hello World $name"
    }
}