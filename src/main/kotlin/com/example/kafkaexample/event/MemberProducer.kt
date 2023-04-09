package com.example.kafkaexample.event

import com.example.kafkaexample.kafka.KafkaTopicConfiguration.Companion.CREATE_MEMBER
import com.example.kafkaexample.kafka.model.CreatedMember
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaTemplate

@Configuration
class MemberProducer(
    private val kafkaTemplate: KafkaTemplate<String, CreatedMember>
) {

    fun send(createdMember: CreatedMember) {
        kafkaTemplate.send(CREATE_MEMBER, createdMember)
    }
}
