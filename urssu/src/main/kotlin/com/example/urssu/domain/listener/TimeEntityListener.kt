package com.example.urssu.domain.listener

import java.time.LocalDateTime
import javax.persistence.PrePersist
import javax.persistence.PreUpdate

class TimeEntityListener {
    @PrePersist
    fun setCreatedAtAndUpdatedAt(o: Any?) {
        if (o is Auditable) {
            o.setCreatedAt(LocalDateTime.now())
            o.setUpdatedAt(LocalDateTime.now())
        }
    }

    @PreUpdate
    fun preUpdate(o: Any?) {
        if (o is Auditable) {
            o.setUpdatedAt(LocalDateTime.now())
        }
    }
}