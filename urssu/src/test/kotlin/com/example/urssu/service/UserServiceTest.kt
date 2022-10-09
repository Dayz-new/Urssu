package com.example.urssu.service

import com.example.urssu.dto.JoinReqUserDto
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
@ExtendWith(MockKExtension::class)
internal class UserServiceTest{

    @MockK
    lateinit var userService: UserService

    @Test
    fun signUpTest(){
        val joinReqUserDto = JoinReqUserDto("abc@naver.com", "0000", "abc")
        every { userService.signUp(joinReqUserDto).email } returns "abc@naver.com"
        println("service.signUp(joinReqUserDto).email = ${userService.signUp(joinReqUserDto).email}")
    }

}