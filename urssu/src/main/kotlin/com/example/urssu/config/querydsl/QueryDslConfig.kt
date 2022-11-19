package com.example.urssu.config.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Component
class QueryDslConfig {

    @PersistenceContext
    lateinit var em: EntityManager

    // JpaQueryFactory를 Bean으로 등록해 프로젝트 전역에서 querydsl 사용할 수 있도록 함.
    @Bean
    fun jpaQueryFactory(): JPAQueryFactory {
        return JPAQueryFactory(em)
    }
}