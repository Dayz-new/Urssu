package com.example.urssu.service

import com.example.urssu.domain.entity.ArticleEntity
import com.example.urssu.domain.entity.CommentEntity
import com.example.urssu.domain.entity.UserEntity
import com.example.urssu.domain.repository.ArticleRepository
import com.example.urssu.domain.repository.CommentRepository
import com.example.urssu.domain.repository.UserRepository
import com.example.urssu.dto.ArticleReqDto
import com.example.urssu.dto.UserInfoDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class ArticleService {

    @Autowired lateinit var articleRepository: ArticleRepository

    @Autowired lateinit var commentRepository: CommentRepository

    @Autowired lateinit var userRepository: UserRepository

    fun postArticle(articleReqDto: ArticleReqDto): ArticleEntity{
        val userEntity: UserEntity = userRepository.findByEmailAndPassword(articleReqDto.email, articleReqDto.password)
        return articleRepository.save(articleReqDto.toEntity(userEntity))
    }

    fun updateArticle(articleReqDto: ArticleReqDto, articleId: Int): ArticleEntity{
        var articleEntity: ArticleEntity =  articleRepository.findById(articleId).get()
        articleEntity = updateArticleUsingDto(articleEntity, articleReqDto)
        return articleRepository.save(articleEntity)
    }

    fun deleteArticle(userInfoDto: UserInfoDto, articleId: Int){ // 이메일 비밀번호 맞는 거 못 찾으면 예외 던지자
        val articles: List<ArticleEntity> = articleRepository.findAllByEmailAndPassword(userInfoDto.email, userInfoDto.password)
        for(article in articles){
            if(article.articleId == articleId) {
                val comments: List<CommentEntity> = commentRepository.findAllByArticleId(article.articleId)
                for (comment in comments) {
                    commentRepository.delete(comment)
                }
                articleRepository.delete(article)
            }
        }
    }

    private fun updateArticleUsingDto(articleEntity: ArticleEntity, articleReqDto: ArticleReqDto): ArticleEntity{
        val userEntity: UserEntity = userRepository.findByEmailAndPassword(articleReqDto.email, articleReqDto.password)
        articleEntity.content = articleReqDto.content
        articleEntity.title = articleReqDto.title
        articleEntity.userEntity = userEntity
        return articleEntity
    }
}