package com.example.urssu.domain.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTimeEntity {

    @CreatedDate
    private val created_at: LocalDateTime? = null

    @LastModifiedDate
    private val updated_at: LocalDateTime? = null

    fun getCreatedAt(): LocalDateTime? {
        return created_at
    }

    fun getUpdatedAt(): LocalDateTime? {
        return updated_at
    }
}