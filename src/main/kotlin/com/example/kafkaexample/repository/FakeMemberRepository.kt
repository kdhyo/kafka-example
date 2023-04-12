package com.example.kafkaexample.repository

import com.example.kafkaexample.domain.Member
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class FakeMemberRepository : MemberRepository {
    private val members = mutableMapOf<Long, Member>()

    @Volatile
    private var id: Long = 0

    override fun persist(member: Member): Member {
        return Member(
            id = id++,
            name = member.name,
            email = member.email,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
        ).also {
            members[it.id] = it
        }
    }

    override fun findById(id: Long): Member? {
        return members[id]
    }

    override fun update(id: Long, name: String): Member {
        val updatedMember = members[id]!!.copy(name = name, updatedAt = LocalDateTime.now())
        members[id] = updatedMember
        return updatedMember
    }

}
