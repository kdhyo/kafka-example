package com.example.kafkaexample.event

import com.example.kafkaexample.kafka.KafkaTopicConfiguration.Companion.CREATE_MEMBER
import com.example.kafkaexample.kafka.model.CreatedMember
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.transaction.annotation.Transactional

@Configuration
class CreatedMemberProducer(
    @Qualifier("createdMemberKafkaTemplate")
    private val kafkaTemplate: KafkaTemplate<String, CreatedMember>
) {

    @Transactional(transactionManager = "createdMemberKafkaTransactionManager")
    fun send(createdMember: CreatedMember) {
        kafkaTemplate.send(CREATE_MEMBER, createdMember)
    }
}
