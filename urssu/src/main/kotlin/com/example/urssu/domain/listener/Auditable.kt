package com.example.urssu.domain.listener

import java.time.LocalDateTime

interface Auditable {
    fun getCreatedAt(): LocalDateTime?
    fun getUpdatedAt(): LocalDateTime?

    fun setCreatedAt(createdAt: LocalDateTime?)
    fun setUpdatedAt(updatedAt: LocalDateTime?)
}