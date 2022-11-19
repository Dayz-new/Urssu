package com.example.urssu.config.user

import com.example.urssu.domain.entity.UserRole
import com.example.urssu.service.CustomUserDetailsService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider {
    @Autowired lateinit var customUserDetailsService: CustomUserDetailsService

    @Value("\${jwt.secret}")
    private var secretKey: String? = null

    private val authTokenValidTime = 240 * 60 * 1000L

    private val refreshTokenValidTime = 24 * 60 * 60 * 1000L



    @PostConstruct
    protected fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey!!.toByteArray())
    }

    //JWT 토큰 생성
    fun createAuthToken(email: String?, role: UserRole?): String {
        val now = Date()
        val claims: Claims = Jwts.claims()
            .setSubject(email)
        claims["role"] = role
        return Jwts.builder()
            .setClaims(claims) // 페이로드
            .setIssuedAt(now) //발행시간
            .setExpiration(Date(now.time + authTokenValidTime)) // 토큰 만료기한
            .signWith(SignatureAlgorithm.HS256, secretKey) // 서명. 사용할 암호화 알고리즘과 signature 에 들어갈 secretKey 세팅
            .compact()
    }

    // Refresh Token 생성
    fun createRefreshToken(email: String?, role: UserRole?): String {
        val now = Date()
        val claims: Claims = Jwts.claims()
            .setSubject(email)
        claims["role"] = role
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + refreshTokenValidTime))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }

    //JWT 토큰에서 인증정보 조회
    fun getAuthentication(token: String?): Authentication {
        val userDetails: UserDetails = customUserDetailsService.loadUserByUsername(getUserPk(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    // 토큰에서 회원 정보를 뽑는다.
    fun getUserPk(token: String?): String {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject()
    }

    // 토큰 검사
    fun validateToken(jwtToken: String?): Boolean {
        return try {
            val claims: Jws<Claims> = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken)
            !claims.getBody().getExpiration().before(Date())
        } catch (e: Exception) {
            false
        }
    }
}