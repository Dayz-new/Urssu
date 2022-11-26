package com.example.urssu.config

enum class BaseResponseStatus(
    val isSuccess: Boolean,
    val code: Int = 0,
    val message: String
) {
    SUCCESS(true, 200, "요청에 성공했습니다."),

    // 회원 관련 에러
    USER_EMPTY_FIELD(false, 400, "모든 필드에 빈 값이 들어갈 수 없습니다."),
    USER_EMPTY_USER(false, 404, "존재하지 않는 유저입니다."),
    USER_ALREADY_EXIST_USER(false, 409, "이미 존재하는 유저입니다."),

    // 게시글 관련 에러
    POST_EMPTY_TITLE_CONTENT(false, 400, "제목과 내용에 빈 값이 들어갈 수 없습니다다."),
    POST_NOT_EXIST_POST(false, 404, "존재하지 않는 게시글입니다."),

    // 댓글 관련 에러
    COMMENT_EMPTY_CONTENT(false, 400, "댓글의 내용에 빈 값이 들어갈 수 없습니다."),

    //로그인 관련 에러
    FAILED_TO_LOGIN(false, 401, "잘못된 로그인 요청입니다.")


}