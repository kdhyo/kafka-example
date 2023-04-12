package com.example.kafkaexample.domain

import java.time.LocalDateTime

data class Member(
    val id: Long = 0,
    val name: String,
    val email: String,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
)
