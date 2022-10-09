package com.example.urssu.controller

import com.example.urssu.config.BaseException
import com.example.urssu.config.BaseResponse
import com.example.urssu.config.BaseResponseStatus
import com.example.urssu.domain.entity.UserEntity
import com.example.urssu.domain.repository.UserRepository
import com.example.urssu.dto.JoinReqUserDto
import com.example.urssu.dto.JoinResUserDto
import com.example.urssu.dto.UserInfoDto
import com.example.urssu.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var userRepository: UserRepository

    @PostMapping("/join")
    fun join(@Valid @RequestBody joinReqUserDto: JoinReqUserDto, bindingResult: BindingResult): BaseResponse<JoinResUserDto>{
        if(bindingResult.hasErrors()){
            return BaseResponse(BaseResponseStatus.USER_EMPTY_FIELD)
        }

        return try{
            val userEntity: UserEntity = userService.signUp(joinReqUserDto)
            BaseResponse(userEntity.toJoinResUserDto())
        } catch (baseException: BaseException){
            BaseResponse(baseException.baseResponseStatus)
        }

    }

    @DeleteMapping("/delete")
    fun delete(@RequestBody userInfoDto: UserInfoDto): BaseResponse<Int> {
        return try {
            userService.deleteUser(userInfoDto)
            BaseResponse(BaseResponseStatus.SUCCESS.code)
        } catch (baseException: BaseException) {
            BaseResponse(baseException.baseResponseStatus)
        }
    }
}