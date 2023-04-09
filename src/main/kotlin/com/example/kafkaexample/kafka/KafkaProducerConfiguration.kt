package com.example.kafkaexample.kafka

import com.example.kafkaexample.kafka.KafkaTopicConfiguration.Companion.CREATE_MEMBER
import com.example.kafkaexample.kafka.model.CreatedMember
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaAdmin.NewTopics
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
@Profile("!test")
class KafkaConfiguration {

    // prod 환경에서는 사용하지 않는 게 좋다.
    @Bean
    fun createMemberTopic(): NewTopics {
        return NewTopics(
            NewTopic(CREATE_MEMBER, 1, 1)
        )
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, CreatedMember> {
        return KafkaTemplate(producerFactory())
    }

    @Bean
    fun producerFactory(): ProducerFactory<String, CreatedMember> {
        return DefaultKafkaProducerFactory(producerConfigs())
    }

    private fun producerConfigs(): Map<String, Any> {
        val configs = HashMap<String, Any>()
        // default options
        configs["acks"] = 1.toString()
        configs["linger.ms"] = 0.toString()
        configs["retries"] = Int.MAX_VALUE.toString()
        configs["max.in.flight.requests.per.connection"] = 5.toString()
        configs["partitioner.class"] = "org.apache.kafka.clients.producer.internals.DefaultPartitioner"
        configs["enable.idempotence"] = false.toString()
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
    }
}
