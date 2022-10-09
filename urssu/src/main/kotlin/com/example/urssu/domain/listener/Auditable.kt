package com.example.urssu.domain.listener

import java.time.LocalDateTime

interface Auditable {
    fun setCreatedAt(createdAt: LocalDateTime?)
    fun setUpdatedAt(updatedAt: LocalDateTime?)
}