package com.example.kafkaexample.event

import com.example.kafkaexample.kafka.KafkaTopicConfiguration.Companion.CREATE_MEMBER
import com.example.kafkaexample.kafka.KafkaTopicConfiguration.Companion.UPDATE_MEMBER
import com.example.kafkaexample.kafka.model.CreatedMember
import org.slf4j.LoggerFactory.getLogger
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload

private val log = getLogger(MemberConsumer::class.java)

@Configuration
class MemberConsumer {

    @KafkaListener(topics = [CREATE_MEMBER], groupId = "member-group", containerFactory = "createdMemberKafkaListenerContainerFactory")
    fun consume(@Payload createdMember: CreatedMember) {
        log.info("Received created member: $createdMember")
    }

    @KafkaListener(topics = [UPDATE_MEMBER], groupId = "member-group", containerFactory = "updatedMemberKafkaListenerContainerFactory")
    fun consume2(@Payload updatedMember: CreatedMember) {
        log.info("Received updated member: $updatedMember")
    }
}
