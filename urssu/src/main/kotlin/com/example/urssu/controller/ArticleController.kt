package com.example.urssu.controller

import com.example.urssu.config.BaseResponseStatus
import com.example.urssu.domain.entity.ArticleEntity
import com.example.urssu.dto.ArticleReqDto
import com.example.urssu.dto.ArticleResDto
import com.example.urssu.dto.UserInfoDto
import com.example.urssu.service.ArticleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/article")
class ArticleController {
    @Autowired private lateinit var articleService: ArticleService

    @PostMapping("/post")
    fun post(@RequestBody articleReqDto: ArticleReqDto): ArticleResDto{
        val articleEntity: ArticleEntity = articleService.postArticle(articleReqDto)
        return articleEntity.toArticleResDto()
    }

    @PatchMapping("/update/{article_id}")
    fun update(@RequestBody articleReqDto: ArticleReqDto, @PathVariable("article_id") articleId: Int): ArticleResDto{
        val articleEntity: ArticleEntity = articleService.updateArticle(articleReqDto, articleId)
        return articleEntity.toArticleResDto()
    }

    @DeleteMapping("/delete/{article_id}")
    fun delete(@RequestBody userInfoDto: UserInfoDto, @PathVariable("article_id") articleId: Int): Int{
        articleService.deleteArticle(userInfoDto, articleId)
        return BaseResponseStatus.SUCCESS.code
    }
}