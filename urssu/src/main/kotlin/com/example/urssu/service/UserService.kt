package com.example.urssu.service

import com.example.urssu.config.BaseException
import com.example.urssu.config.BaseResponseStatus
import com.example.urssu.config.user.JwtTokenProvider
import com.example.urssu.domain.entity.ArticleEntity
import com.example.urssu.domain.entity.CommentEntity
import com.example.urssu.domain.entity.UserEntity
import com.example.urssu.domain.entity.UserRole
import com.example.urssu.domain.repository.ArticleRepository
import com.example.urssu.domain.repository.CommentRepository
import com.example.urssu.domain.repository.UserRepository
import com.example.urssu.dto.user.JoinReqUserDto
import com.example.urssu.dto.user.LoginReqUserDto
import com.example.urssu.dto.user.LoginResUserDto
import com.example.urssu.dto.user.UserInfoDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


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


    fun deleteUser(userInfoDto: UserInfoDto){
        if(userRepository.findByEmail(userInfoDto.email).isEmpty) {
            val baseException = BaseException(BaseResponseStatus.USER_EMPTY_USER)
            throw baseException
        }

        val userEntity: UserEntity = userRepository.findByEmail(userInfoDto.email).get()

        val articles: List<ArticleEntity> = articleRepository.findAllByEmailAndPassword(userEntity.email, userEntity.password)
        for(article in articles){
            articleService.deleteArticle(userEntity.toUserInfoDto(), article.articleId)
        }

        val comments: List<CommentEntity?> = commentRepository.findAllByEmailAndPassword(userEntity.email, userEntity.password)
        for(comment in comments){
            commentRepository.delete(comment)
        }

        userRepository.delete(userEntity)

    }
}