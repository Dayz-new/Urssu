package com.example.urssu.config.user

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAuthenticationEntryPoint():
AuthenticationEntryPoint{
    @Throws(IOException::class, ServletException::class)
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        authException: AuthenticationException?
    ) {
        response.sendRedirect("/exception/entrypoint")
    } //예외가 발생했을 때, Controller에 있는 해당 URI로 전달해주게 됩니다.

}