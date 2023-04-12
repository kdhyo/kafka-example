package com.example.kafkaexample.event

import com.example.kafkaexample.kafka.KafkaTopicConfiguration.Companion.UPDATE_MEMBER
import com.example.kafkaexample.kafka.model.UpdatedMember
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.transaction.annotation.Transactional

@Configuration
class UpdatedMemberProducer(
    @Qualifier("updatedMemberKafkaTemplate")
    private val kafkaTemplate: KafkaTemplate<String, UpdatedMember>
) {

    @Transactional(transactionManager = "updatedMemberKafkaTransactionManager")
    fun send(updatedMember: UpdatedMember) {
        kafkaTemplate.send(UPDATE_MEMBER, updatedMember)
    }
}
