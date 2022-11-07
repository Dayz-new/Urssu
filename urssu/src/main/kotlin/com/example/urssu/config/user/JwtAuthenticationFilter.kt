package com.example.urssu.config.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtAuthenticationFilter(
    @Autowired jwtTokenProvider: JwtTokenProvider, authenticationManager: AuthenticationManager
) : BasicAuthenticationFilter(authenticationManager) {
    private var jwtTokenProvider = jwtTokenProvider

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val bearer = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (bearer == null || !bearer.startsWith("Bearer ") || !jwtTokenProvider.validateToken(bearer)) {
            chain.doFilter(request, response)
            return
        }
        val token = bearer.substring("Bearer ".length)
        val authentication = jwtTokenProvider.getAuthentication(token)
        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(request, response)
    }

}
