package com.example.kafkaexample.repository

import com.example.kafkaexample.domain.Member
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository {
    fun persist(member: Member): Member
    fun findById(id: Long): Member?
    fun update(id: Long, name: String): Member
}
