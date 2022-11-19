package com.example.urssu.dto.user

import org.jetbrains.annotations.Nullable
import java.time.LocalDateTime

data class ShowReqUserDto (
    @Nullable
    val username: String,
    @Nullable
    val email: String,
    @Nullable
    val createdAtStart: LocalDateTime,
    @Nullable
    val createdAtEnt: LocalDateTime,
    @Nullable
    val updatedAtStart: LocalDateTime,
    @Nullable
    val updatedAtEnd: LocalDateTime
)