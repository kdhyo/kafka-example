package com.example.kafkaexample.kafka.model

import com.example.kafkaexample.domain.Member

data class UpdatedMember(
    val id: Long,
    val name: String,
    val email: String,
    val updatedAt: String
) {
    companion object {
        fun of(updatedMember: Member): UpdatedMember {
            return UpdatedMember(
                id = updatedMember.id,
                name = updatedMember.name,
                email = updatedMember.email,
                updatedAt = updatedMember.updatedAt.toString()
            )
        }
    }
}
