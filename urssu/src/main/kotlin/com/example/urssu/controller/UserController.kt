package com.example.urssu.controller

import com.example.urssu.config.BaseResponseStatus
import com.example.urssu.domain.entity.UserEntity
import com.example.urssu.dto.JoinReqUserDto
import com.example.urssu.dto.JoinResUserDto
import com.example.urssu.dto.UserInfoDto
import com.example.urssu.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    private lateinit var userService: UserService

    @PostMapping("/join")
    fun join(@RequestBody joinReqUserDto: JoinReqUserDto): JoinResUserDto{
        val userEntity: UserEntity = userService.signUp(joinReqUserDto)
        return userEntity.toJoinResUserDto()
    }

    @DeleteMapping("/delete")
    fun delete(@RequestBody userInfoDto: UserInfoDto): Int {
        userService.deleteUser(userInfoDto)
        return BaseResponseStatus.SUCCESS.code
    }

}