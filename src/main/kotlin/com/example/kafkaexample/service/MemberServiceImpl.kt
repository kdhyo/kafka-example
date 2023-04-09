package com.example.kafkaexample.service

import com.example.kafkaexample.domain.Member
import com.example.kafkaexample.event.MemberProducer
import com.example.kafkaexample.kafka.model.CreatedMember
import com.example.kafkaexample.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberServiceImpl(
    private val memberRepository: MemberRepository,
    private val memberProducer: MemberProducer,
) : MemberService {

    override fun createMember(name: String, email: String): Member {
        val member = Member(
            name = name,
            email = email
        )
        val persistedMember = memberRepository.persist(member)
        memberProducer.send(CreatedMember.of(persistedMember))
        return persistedMember
    }
}
