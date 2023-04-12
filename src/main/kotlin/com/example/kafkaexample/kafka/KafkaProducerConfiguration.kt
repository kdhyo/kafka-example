package com.example.kafkaexample.kafka

import com.example.kafkaexample.kafka.KafkaTopicConfiguration.Companion.CREATE_MEMBER
import com.example.kafkaexample.kafka.KafkaTopicConfiguration.Companion.UPDATE_MEMBER
import com.example.kafkaexample.kafka.model.CreatedMember
import com.example.kafkaexample.kafka.model.UpdatedMember
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.producer.ProducerConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaAdmin.NewTopics
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.transaction.KafkaTransactionManager
import java.util.UUID

@Configuration
@Profile("!test")
class KafkaConfiguration {

    // prod 환경에서는 사용하지 않는 게 좋다.
    @Bean
    fun createMemberTopic(): NewTopics {
        return NewTopics(
            NewTopic(CREATE_MEMBER, 1, 1),
            NewTopic(UPDATE_MEMBER, 1, 1)
        )
    }

    @Primary
    @Bean("createdMemberKafkaTransactionManager")
    fun createdMemberKafkaTransactionManager(): KafkaTransactionManager<String, CreatedMember> {
        return KafkaTransactionManager(createdMemberProducerFactory())
    }

    @Bean("updatedMemberKafkaTransactionManager")
    fun updatedMemberKafkaTransactionManager(): KafkaTransactionManager<String, UpdatedMember> {
        return KafkaTransactionManager(updatedMemberProducerFactory())
    }

    @Bean("createdMemberKafkaTemplate")
    fun createdMemberKafkaTemplate(): KafkaTemplate<String, CreatedMember> {
        return KafkaTemplate(createdMemberProducerFactory())
    }

    @Bean("updatedMemberKafkaTemplate")
    fun updatedMemberKafkaTemplate(): KafkaTemplate<String, UpdatedMember> {
        return KafkaTemplate(updatedMemberProducerFactory())
    }

    private fun createdMemberProducerFactory(): ProducerFactory<String, CreatedMember> {
        return DefaultKafkaProducerFactory(
            defaultConfigs().apply {
                this[ProducerConfig.TRANSACTIONAL_ID_CONFIG] = "create-member-${UUID.randomUUID()}"
            }
        )
    }

    private fun updatedMemberProducerFactory(): ProducerFactory<String, UpdatedMember> {
        return DefaultKafkaProducerFactory(
            defaultConfigs().apply {
                this[ProducerConfig.TRANSACTIONAL_ID_CONFIG] = "update-member-${UUID.randomUUID()}"
            }
        )
    }

    private fun defaultConfigs(): HashMap<String, Any> {
        val configs = HashMap<String, Any>()
        // default options
        configs["acks"] = "-1"
        configs["linger.ms"] = 0.toString()
        configs["retries"] = Int.MAX_VALUE.toString()
        configs["max.in.flight.requests.per.connection"] = 5.toString()
        configs["partitioner.class"] = "org.apache.kafka.clients.producer.internals.DefaultPartitioner"
        configs["enable.idempotence"] = true.toString()
        configs["bootstrap.servers"] = "localhost:9092"
        configs["key.serializer"] = "org.apache.kafka.common.serialization.StringSerializer"
        // custom options
        configs["value.serializer"] = "org.springframework.kafka.support.serializer.JsonSerializer" // default: String
        return configs
    }
}

class KafkaTopicConfiguration {
    companion object {
        const val CREATE_MEMBER = "create.member"
        const val UPDATE_MEMBER = "update.member"
    }
}
