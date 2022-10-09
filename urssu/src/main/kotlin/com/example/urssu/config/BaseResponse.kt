package com.example.urssu.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder("isSuccess", "code", "message", "result")
data class BaseResponse<T>(
    var isSuccess: Boolean,
    var message: String,
    var code: Int,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var result: T? = null
) {

    constructor (
        result: T
    ): this(BaseResponseStatus.SUCCESS.isSuccess, BaseResponseStatus.SUCCESS.message, BaseResponseStatus.SUCCESS.code, result)

    constructor(
        baseResponseStatus: BaseResponseStatus
    ): this(baseResponseStatus.isSuccess, baseResponseStatus.message, baseResponseStatus.code)
}