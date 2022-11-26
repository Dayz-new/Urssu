package com.example.urssu.service

import com.example.urssu.config.BaseException
import com.example.urssu.config.BaseResponseStatus
import com.example.urssu.config.resolver.AuthInfo
import com.example.urssu.config.user.JwtTokenProvider
import com.example.urssu.domain.entity.ArticleEntity
import com.example.urssu.domain.entity.CommentEntity
import com.example.urssu.domain.entity.UserEntity
import com.example.urssu.domain.entity.UserRole
import com.example.urssu.domain.repository.ArticleRepository
import com.example.urssu.domain.repository.CommentRepository
import com.example.urssu.domain.repository.user.UserRepository
import com.example.urssu.dto.user.*
import org.apache.catalina.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.stream.Collectors


@Transactional
@Service
class UserService{

    @Autowired lateinit var jwtTokenProvider: JwtTokenProvider

    @Autowired lateinit var passwordEncoder: PasswordEncoder

    @Autowired lateinit var userRepository: UserRepository

    @Autowired lateinit var articleRepository: ArticleRepository

    @Autowired lateinit var commentRepository: CommentRepository

    @Autowired lateinit var articleService: ArticleService

    fun signUp(joinReqUserDto: JoinReqUserDto): UserEntity {
        if(userRepository.findByEmail(joinReqUserDto.email).isPresent){
            val baseException = BaseException(BaseResponseStatus.USER_ALREADY_EXIST_USER)
            throw baseException
        }
        joinReqUserDto.password = passwordEncoder.encode(joinReqUserDto.password)
        return userRepository.save(joinReqUserDto.toEntity())
    }

    @Throws(BaseException::class)
    fun login(loginReqUserDto: LoginReqUserDto): LoginResUserDto {
        var userEntity = userRepository.findByEmail(loginReqUserDto.email).orElseThrow {
            BaseException(
                BaseResponseStatus.FAILED_TO_LOGIN
            )
        }
        if (!passwordEncoder.matches(loginReqUserDto.password, userEntity.password)) {
            throw BaseException(BaseResponseStatus.FAILED_TO_LOGIN)
        }
        val email: String = userEntity.email
        val role: UserRole = userEntity.role

        val authTokenResult: String = jwtTokenProvider.createAuthToken(email, role)
        val refreshTokenResult: String = jwtTokenProvider.createRefreshToken(email, role)

        userEntity.refreshToken = refreshTokenResult
        userRepository.save(userEntity)

        val loginResUserDto: LoginResUserDto = LoginResUserDto(userEntity.email, userEntity.username, userEntity.role,
        authTokenResult, userEntity.refreshToken!!
        )

        return loginResUserDto
    }


    fun deleteUser(authInfo: AuthInfo){
        if(userRepository.findByEmail(authInfo.email).isEmpty) {
            val baseException = BaseException(BaseResponseStatus.USER_EMPTY_USER)
            throw baseException
        }

        val userEntity: UserEntity = userRepository.findByEmail(authInfo.email).get()

        val articles: List<ArticleEntity> = articleRepository.findAllByEmail(userEntity.email)
        for(article in articles){
            articleService.deleteArticle(authInfo, article.articleId)
        }

        val comments: List<CommentEntity?> = commentRepository.findAllByEmail(authInfo.email)
        for(comment in comments){
            commentRepository.delete(comment)
        }

        userRepository.delete(userEntity)

    }

    fun showUser(username: String?, email: String?, createdAtStart: String?, createdAtEnd: String?, updatedAtStart: String?, updatedAtEnd: String?): List<UserInfoDto> {
        var createdAtStartTemp: String? = createdAtStart
        if(createdAtStart == null){
            createdAtStartTemp = "0001-01-01T00:00:00"
        }else{
            createdAtStartTemp = createdAtStart + "T00:00:00"
        }

        var createdAtEndTemp: String? = createdAtEnd
        if(createdAtEnd == null){
            createdAtEndTemp = "9999-12-31T23:59:59"
        }else{
            createdAtEndTemp = createdAtEnd + "T23:59:59"
        }

        var updatedAtStartTemp: String? = updatedAtStart
        if(updatedAtStart == null){
            updatedAtStartTemp = "0001-01-01T00:00:00"
        }else{
            updatedAtStartTemp = updatedAtStart + "T00:00:00"
        }

        var updatedAtEndTemp: String? = updatedAtEnd
        if(updatedAtEnd == null){
            updatedAtEndTemp = "9999-12-31T23:59:59"
        }else{
            updatedAtEndTemp = updatedAtEnd + "T23:59:59"
        }

        var createdAtStartLDT: LocalDateTime = LocalDateTime.parse(createdAtStartTemp, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        var createdAtEndLDT: LocalDateTime = LocalDateTime.parse(createdAtEndTemp, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        var updatedAtStartLDT: LocalDateTime = LocalDateTime.parse(updatedAtStartTemp, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        var updatedAtEndLDT: LocalDateTime = LocalDateTime.parse(updatedAtEndTemp, DateTimeFormatter.ISO_LOCAL_DATE_TIME)

        var users: MutableList<UserEntity> = mutableListOf()

        var usersByTime: MutableList<UserEntity> = userRepository.findALlByTime(createdAtStartLDT,createdAtEndLDT,updatedAtStartLDT,updatedAtEndLDT)

        users = usersByTime

        if(users.isEmpty()){
            val baseException = BaseException(BaseResponseStatus.USER_EMPTY_USER)
            throw baseException
        }

        if(username != null){
            var userByUsername = usersByTime.find { user -> user.username == username }
            users.clear()
            users.add(userByUsername!!)
        }

        if(email != null){
            var userByEmail = usersByTime.find { user -> user.email == email }
            users.clear()
            users.add(userByEmail!!)
        }

        var userInfoDtoList: List<UserInfoDto> = users.stream()
            .map(UserEntity::toUserInfoDto)
            .collect(Collectors.toList())

        return userInfoDtoList
    }

    fun checkAdmin(authInfo: AuthInfo) {
        val userEntity = userRepository.findByEmail(authInfo.email).get()
        if(userEntity.role == UserRole.ROLE_USER){
            throw Exception()
        } else{
            return
        }
    }


}