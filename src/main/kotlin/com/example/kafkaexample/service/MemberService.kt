package com.example.kafkaexample.service

import com.example.kafkaexample.domain.Member

interface MemberService {

    fun createMember(name: String, email: String): Member
}
