package com.example.urssu.controller

import com.example.urssu.config.BaseException
import com.example.urssu.config.BaseResponse
import com.example.urssu.config.BaseResponseStatus
import com.example.urssu.config.resolver.Auth
import com.example.urssu.config.resolver.AuthInfo
import com.example.urssu.config.user.JwtTokenProvider
import com.example.urssu.domain.entity.UserEntity
import com.example.urssu.dto.user.*
import com.example.urssu.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
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

    @PostMapping("/login")
    fun login(@RequestBody loginReqUserDto: LoginReqUserDto): BaseResponse<LoginResUserDto>{
        return try{
            val loginResUserDto: LoginResUserDto = userService.login(loginReqUserDto)
            BaseResponse(loginResUserDto)
        } catch (baseException: BaseException){
            BaseResponse(baseException.baseResponseStatus)
        }
    }

    @DeleteMapping("/delete")
    fun delete(@Auth authInfo: AuthInfo): BaseResponse<Int> {
        return try {
            userService.deleteUser(authInfo)
            BaseResponse(BaseResponseStatus.SUCCESS.code)
        } catch (baseException: BaseException) {
            BaseResponse(baseException.baseResponseStatus)
        }
    }

    @GetMapping("/show")
    fun show(@Auth authInfo: AuthInfo, showReqUserDto: ShowReqUserDto): BaseResponse<List<UserInfoDto>>{
        return try{
            userService.checkAdmin(authInfo)
            val users = userService.showUser(showReqUserDto)
            BaseResponse(users)
        } catch (baseException: BaseException){
            BaseResponse(baseException.baseResponseStatus)
        }
    }
}