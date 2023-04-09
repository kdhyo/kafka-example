package com.example.kafkaexample.repository

import com.example.kafkaexample.domain.Member
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class FakeMemberRepository : MemberRepository {
    private val members = mutableListOf<Member>()
    @Volatile
    private var id: Long = 0

    override fun persist(member: Member): Member {
        return Member(
            id = id++,
            name = member.name,
            email = member.email,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
        ).apply {
            members.add(this)
        }
    }
}
