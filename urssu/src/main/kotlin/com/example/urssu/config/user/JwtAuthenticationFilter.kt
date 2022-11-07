package com.example.urssu.config.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest


class JwtAuthenticationFilter(
    @Autowired jwtTokenProvider: JwtTokenProvider
) : GenericFilterBean() {
    private var jwtTokenProvider = jwtTokenProvider

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        // 헤더에서 JWT 를 받아옵니다.
        val token = jwtTokenProvider!!.resolveToken((request as HttpServletRequest))

        // 유효한 토큰인지 확인합니다. 유효성검사
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 토큰 인증과정을 거친 결과를 authentication이라는 이름으로 저장해줌.
            val authentication = jwtTokenProvider.getAuthentication(token)
            // SecurityContext 에 Authentication 객체를 저장합니다.
            // token이 인증된 상태를 유지하도록 context(맥락)을 유지해줌
            SecurityContextHolder.getContext().authentication = authentication
        }
        //UsernamePasswordAuthenticationFilter로 이동
        chain.doFilter(request, response)
    }
}