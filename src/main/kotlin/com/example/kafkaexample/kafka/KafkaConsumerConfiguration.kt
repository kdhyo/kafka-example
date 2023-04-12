package com.example.kafkaexample.kafka

import com.example.kafkaexample.kafka.model.CreatedMember
import com.example.kafkaexample.kafka.model.UpdatedMember
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
import org.springframework.kafka.support.serializer.JsonDeserializer

@Configuration
class KafkaConsumerConfiguration {

    @Bean
    fun createdMemberKafkaListenerContainerFactory(): KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, CreatedMember>> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, CreatedMember>()
            .apply {
                consumerFactory = createdMemberConsumerFactory()
            }
        return factory
    }

    @Bean
    fun updatedMemberKafkaListenerContainerFactory(): KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, UpdatedMember>> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, UpdatedMember>()
            .apply {
                consumerFactory = updatedMemberConsumerFactory()
            }
        return factory
    }

    @Bean
    fun createdMemberConsumerFactory(): ConsumerFactory<String, CreatedMember> {
        val deserializer = JsonDeserializer(CreatedMember::class.java).apply {
            addTrustedPackages("*")
            setUseTypeHeaders(false)
        }
        return DefaultKafkaConsumerFactory(consumerConfigs(), StringDeserializer(), deserializer)
    }

    @Bean
    fun updatedMemberConsumerFactory(): ConsumerFactory<String, UpdatedMember> {
        val deserializer = JsonDeserializer(UpdatedMember::class.java).apply {
            addTrustedPackages("*")
            setUseTypeHeaders(false)
        }
        return DefaultKafkaConsumerFactory(consumerConfigs(), StringDeserializer(), deserializer)
    }

    private fun consumerConfigs(): Map<String, Any> {
        val configs = HashMap<String, Any>()
        // default options
        configs["bootstrap.servers"] = "localhost:9092"
        configs["key.deserializer"] = "org.apache.kafka.common.serialization.StringDeserializer"
        configs["group.id"] = "test-group"
        configs["enable.auto.commit"] = true
        configs["auto.commit.interval.ms"] = 5000
        configs["max.poll.records"] = 500
        configs["session.timeout.ms"] = 10000
        configs["heartbeat.interval.ms"] = 3000
        configs["max.poll.interval.ms"] = 300000
        configs["isolation.level"] = "read_uncommitted"
        // custom options
        configs["auto.offset.reset"] = "earliest" // default: latest
        configs["value.deserializer"] = "org.springframework.kafka.support.serializer.JsonDeserializer" // default: String
        return configs
    }
}
