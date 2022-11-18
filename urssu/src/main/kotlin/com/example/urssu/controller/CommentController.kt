package com.example.urssu.controller

import com.example.urssu.config.BaseException
import com.example.urssu.config.BaseResponse
import com.example.urssu.config.BaseResponseStatus
import com.example.urssu.config.resolver.Auth
import com.example.urssu.config.resolver.AuthInfo
import com.example.urssu.domain.entity.CommentEntity
import com.example.urssu.dto.comment.CommentReqDto
import com.example.urssu.dto.comment.CommentResDto
import com.example.urssu.service.CommentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/comment")
class CommentController {

    @Autowired private lateinit var commentService: CommentService

    @PostMapping("/post/{article_id}")
    fun post(@Valid @RequestBody commentReqDto: CommentReqDto, @PathVariable("article_id") articleId: Int, @Auth authInfo: AuthInfo, bindingResult: BindingResult): BaseResponse<CommentResDto> {
        if(bindingResult.hasErrors()){
            return BaseResponse(BaseResponseStatus.COMMENT_EMPTY_CONTENT)
        }

        return try{
            val commentEntity: CommentEntity = commentService.postComment(commentReqDto, articleId, authInfo)
            BaseResponse(commentEntity.toCommentResDto())
        } catch (baseException: BaseException){
            BaseResponse(baseException.baseResponseStatus)
        }

    }

    @PatchMapping("/update/{article_id}/{comment_id}")
    fun update(@Valid @RequestBody commentReqDto: CommentReqDto, @PathVariable("article_id") articleId: Int, @PathVariable("comment_id") commentId: Int, @Auth authInfo: AuthInfo, bindingResult: BindingResult): BaseResponse<CommentResDto> {
        if(bindingResult.hasErrors()){
            return BaseResponse(BaseResponseStatus.COMMENT_EMPTY_CONTENT)
        }

        return try{
            val commentEntity: CommentEntity = commentService.updateComment(commentReqDto, articleId, commentId, authInfo)
            BaseResponse(commentEntity.toCommentResDto())
        } catch (baseException: BaseException){
            BaseResponse(baseException.baseResponseStatus)
        }

    }

    @DeleteMapping("/delete/{article_id}/{comment_id}")
    fun delete(@PathVariable("article_id") articleId: Int, @PathVariable("comment_id") commentId: Int, @Auth authInfo: AuthInfo): BaseResponse<Int>{
        return try{
            commentService.deleteComment(articleId, commentId, authInfo)
            BaseResponse(BaseResponseStatus.SUCCESS.code)
        } catch (baseException: BaseException){
            BaseResponse(baseException.baseResponseStatus)
        }

    }
}