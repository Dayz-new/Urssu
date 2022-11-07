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

        //private claims
        //claims.put("id", id); // 정보는 key - value 쌍으로 저장.
        claims.put("role", role)
        return Jwts.builder()
            .setClaims(claims) // 페이로드
            .setIssuedAt(now) //발행시간
            .setExpiration(Date(now.time + authTokenValidTime)) // 토큰 만료기한
            .signWith(SignatureAlgorithm.HS256, secretKey) // 서명. 사용할 암호화 알고리즘과 signature 에 들어갈 secretKey 세팅
            .compact()
    }

    fun createRefreshToken(email: String?, role: UserRole?): String {
        val now = Date()
        val claims: Claims = Jwts.claims()
            .setSubject(email)

        //private claims
        //claims.put("id", id); // 정보는 key - value 쌍으로 저장.
        claims.put("role", role)
        return Jwts.builder()
            .setClaims(claims) // 페이로드
            .setIssuedAt(now) //발행시간
            .setExpiration(Date(now.time + refreshTokenValidTime)) // 토큰 만료기한
            .signWith(SignatureAlgorithm.HS256, secretKey) // 서명. 사용할 암호화 알고리즘과 signature 에 들어갈 secretKey 세팅
            .compact()
    }

    //JWT 토큰에서 인증정보 조회
    fun getAuthentication(token: String?): Authentication {
        val userDetails: UserDetails = customUserDetailsService.loadUserByUsername(getUserPk(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    // 토큰에서 회원 정보 추출
    fun getUserPk(token: String?): String {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject()
    }

    // Request의 Header에서 token 값을 가져옵니다. "Authorization" : "TOKEN값'
    fun resolveToken(request: HttpServletRequest): String {
        return request.getHeader("X-AUTH-TOKEN")
    }

    // 토큰의 유효성 + 만료일자 확인  // -> 토큰이 expire되지 않았는지 True/False로 반환해줌.
    fun validateToken(jwtToken: String?): Boolean {
        return try {
            val claims: Jws<Claims> = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken)
            !claims.getBody().getExpiration().before(Date())
        } catch (e: Exception) {
            false
        }
    }
}