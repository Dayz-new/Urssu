package com.example.urssu.controller

import com.example.urssu.config.BaseException
import com.example.urssu.config.BaseResponse
import com.example.urssu.config.BaseResponseStatus
import com.example.urssu.domain.entity.ArticleEntity
import com.example.urssu.dto.ArticleReqDto
import com.example.urssu.dto.ArticleResDto
import com.example.urssu.dto.UserInfoDto
import com.example.urssu.service.ArticleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/article")
class ArticleController {
    @Autowired private lateinit var articleService: ArticleService

    @PostMapping("/post")
    fun post(@Valid @RequestBody articleReqDto: ArticleReqDto, bindingResult: BindingResult): BaseResponse<ArticleResDto>{
        if(bindingResult.hasErrors()){
            return BaseResponse(BaseResponseStatus.POST_EMPTY_TITLE_CONTENT)
        }
        return try{
            val articleEntity: ArticleEntity = articleService.postArticle(articleReqDto)
            BaseResponse(articleEntity.toArticleResDto())
        } catch (baseException: BaseException){
            BaseResponse(baseException.baseResponseStatus)
        }

    }

    @PatchMapping("/update/{article_id}")
    fun update(@Valid @RequestBody articleReqDto: ArticleReqDto, bindingResult: BindingResult, @PathVariable("article_id") articleId: Int): BaseResponse<ArticleResDto>{
        if(bindingResult.hasErrors()){
            return BaseResponse(BaseResponseStatus.POST_EMPTY_TITLE_CONTENT)
        }
        return try{
            val articleEntity: ArticleEntity = articleService.updateArticle(articleReqDto, articleId)
            BaseResponse(articleEntity.toArticleResDto())
        } catch (baseException: BaseException){
            BaseResponse(baseException.baseResponseStatus)
        }

    }

    @DeleteMapping("/delete/{article_id}")
    fun delete(@RequestBody userInfoDto: UserInfoDto, @PathVariable("article_id") articleId: Int): BaseResponse<Int>{
        return try{
            articleService.deleteArticle(userInfoDto, articleId)
            BaseResponse(BaseResponseStatus.SUCCESS.code)
        } catch (baseException: BaseException){
            BaseResponse(baseException.baseResponseStatus)
        }

    }
}