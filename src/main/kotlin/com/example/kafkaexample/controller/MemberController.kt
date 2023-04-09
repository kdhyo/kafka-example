package com.example.kafkaexample.controller

import com.example.kafkaexample.domain.Member
import com.example.kafkaexample.service.MemberService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/members")
class MemberController(
    private val memberService: MemberService
) {

    @PostMapping
    fun createMember(@RequestBody memberRequest: CreateMemberRequest): CreateMemberResponse {
        val createMember = memberService.createMember(memberRequest.name, memberRequest.email)
        return CreateMemberResponse.of(createMember)
    }
}

data class CreateMemberRequest(
    val name: String,
    val email: String
)

data class CreateMemberResponse(
    val id: Long,
    val name: String,
    val email: String,
    val createdAt: String,
) {
    companion object {
        fun of(member: Member): CreateMemberResponse {
            return CreateMemberResponse(
                id = member.id,
                name = member.name,
                email = member.email,
                createdAt = member.createdAt.toString(),
            )
        }
    }
}
