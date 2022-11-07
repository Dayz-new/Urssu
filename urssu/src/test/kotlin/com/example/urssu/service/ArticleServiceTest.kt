package com.example.urssu.service

import com.example.urssu.dto.article.ArticleReqDto
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@ExtendWith(MockKExtension::class)
internal class ArticleServiceTest{
    @MockK
    lateinit var articleService: ArticleService

    @Test
    fun articleServiceTest() {
        val articleReqDto = ArticleReqDto("email@urssu.com","password","title-email","content-email")
        every{ articleService.postArticle(articleReqDto).title} returns "title-email"
        println("articleService.postArticle(articleReqDto).title = ${articleService.postArticle(articleReqDto).title}")
    }
}