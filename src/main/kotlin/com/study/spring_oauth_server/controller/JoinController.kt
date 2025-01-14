package com.study.spring_oauth_server.controller

import com.study.spring_oauth_server.dto.JoinDTO
import com.study.spring_oauth_server.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class JoinController(
    private val userService: UserService
) {
    @GetMapping("/join")
    fun joinPage():String = "joinPage"

    @PostMapping("/join")
    @ResponseBody
    fun join(joinDTO: JoinDTO):String {
        userService.join(joinDTO)
        return "OK"
    }
}