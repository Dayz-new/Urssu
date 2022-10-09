package com.example.urssu.controller

import com.example.urssu.config.BaseException
import com.example.urssu.config.BaseResponse
import com.example.urssu.config.BaseResponseStatus
import com.example.urssu.domain.entity.ArticleEntity
import com.example.urssu.domain.entity.CommentEntity
import com.example.urssu.dto.*
import com.example.urssu.service.CommentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comment")
class CommentController {

    @Autowired private lateinit var commentService: CommentService

    @PostMapping("/post/{article_id}")
    fun post(@RequestBody commentReqDto: CommentReqDto, @PathVariable("article_id") articleId: Int): BaseResponse<CommentResDto> {
        return try{
            val commentEntity: CommentEntity = commentService.postComment(commentReqDto, articleId)
            BaseResponse(commentEntity.toCommentResDto())
        } catch (baseException: BaseException){
            BaseResponse(baseException.baseResponseStatus)
        }

    }

    @PatchMapping("/update/{article_id}/{comment_id}")
    fun update(@RequestBody commentReqDto: CommentReqDto, @PathVariable("article_id") articleId: Int, @PathVariable("comment_id") commentId: Int): BaseResponse<CommentResDto> {
        return try{
            val commentEntity: CommentEntity = commentService.updateComment(commentReqDto, articleId, commentId)
            BaseResponse(commentEntity.toCommentResDto())
        } catch (baseException: BaseException){
            BaseResponse(baseException.baseResponseStatus)
        }

    }

    @DeleteMapping("/delete/{article_id}/{comment_id}")
    fun delete(@RequestBody userInfoDto: UserInfoDto, @PathVariable("article_id") articleId: Int, @PathVariable("comment_id") commentId: Int): BaseResponse<Int>{
        return try{
            commentService.deleteComment(userInfoDto, articleId, commentId)
            BaseResponse(BaseResponseStatus.SUCCESS.code)
        } catch (baseException: BaseException){
            BaseResponse(baseException.baseResponseStatus)
        }

    }
}