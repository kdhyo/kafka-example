package com.example.kafkaexample.kafka.model

import com.example.kafkaexample.domain.Member

data class CreatedMember(
    val id: Long,
    val name: String,
    val email: String,
    val createdAt: String,
) {
    companion object {
        fun of(persistedMember: Member): CreatedMember {
            return CreatedMember(
                id = persistedMember.id,
                name = persistedMember.name,
                email = persistedMember.email,
                createdAt = persistedMember.createdAt.toString(),
            )
        }
    }
}
