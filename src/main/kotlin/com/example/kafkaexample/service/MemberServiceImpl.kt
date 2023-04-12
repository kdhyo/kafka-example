package com.example.kafkaexample.service

import com.example.kafkaexample.domain.Member
import com.example.kafkaexample.event.CreatedMemberProducer
import com.example.kafkaexample.event.UpdatedMemberProducer
import com.example.kafkaexample.kafka.model.CreatedMember
import com.example.kafkaexample.kafka.model.UpdatedMember
import com.example.kafkaexample.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberServiceImpl(
    private val memberRepository: MemberRepository,
    private val createdMemberProducer: CreatedMemberProducer,
    private val updatedMemberProducer: UpdatedMemberProducer
) : MemberService {

    override fun createMember(name: String, email: String): Member {
        val member = Member(
            name = name,
            email = email
        )
        val persistedMember = memberRepository.persist(member)
        createdMemberProducer.send(CreatedMember.of(persistedMember))
        return persistedMember
    }

    override fun findById(id: Long): Member? {
        return memberRepository.findById(id)
    }

    override fun update(id: Long, name: String): Member {
        val updatedMember = memberRepository.update(id, name)
        updatedMemberProducer.send(UpdatedMember.of(updatedMember))
        return updatedMember
    }
}
