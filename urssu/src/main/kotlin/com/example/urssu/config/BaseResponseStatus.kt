package com.example.urssu.config

enum class BaseResponseStatus(
    val isSuccess: Boolean,
    val code: Int = 0,
    val message: String
) {
    SUCCESS(true, 200, "요청에 성공했습니다.")

    


}