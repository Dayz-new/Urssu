package com.example.urssu.config

data class BaseException (
    val baseResponseStatus: BaseResponseStatus
) : Exception()