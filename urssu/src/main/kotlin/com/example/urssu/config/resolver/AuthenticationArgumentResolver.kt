package com.example.urssu.config.resolver

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.core.MethodParameter
import org.springframework.security.jwt.Jwt
import org.springframework.security.jwt.JwtHelper
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer


@Component
class AuthenticationArgumentResolver : HandlerMethodArgumentResolver {

    var mapper: ObjectMapper = ObjectMapper().registerModules(KotlinModule())


    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.getParameterAnnotation(Auth::class.java) != null && parameter.parameterType == AuthInfo::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val authorizationHeader = webRequest.getHeader("Authorization")
        val jwtToken = authorizationHeader!!.substring(7)

        val decodedToken: Jwt = JwtHelper.decode(jwtToken)
        val claims: Map<*,*> = mapper.readValue(decodedToken.claims, Map::class.java)

        var email = claims.get("sub")!!
        var authInfo: AuthInfo = AuthInfo(email as String)
        return authInfo
        /**
         * 토큰에서 이메일 값 얻어오는 로직
         */
    }
}

// eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzZGgxMDBAdXJzc3UuY29tIiwicm9sZSI6IlJPTEVfVVNFUiIsImlhdCI6MTY2ODc1NDE4NCwiZXhwIjoxNjY4NzY4NTg0fQ.DdKBaufKBQXpHQ81Gtyp3-qC1myuQYt2mU_v1QgTybE