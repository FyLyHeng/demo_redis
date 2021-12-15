package com.example.demo_redis.config

import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.JedisPoolingClientConfigurationBuilder
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.GenericToStringSerializer
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer


@Configuration
@EnableRedisRepositories
@Primary
class RedisConfig {


    @Bean
    fun redisPoolConfig(): JedisClientConfiguration {
        val JedisPoolingClientConfigurationBuilder = JedisClientConfiguration
                .builder() as JedisPoolingClientConfigurationBuilder
        val GenericObjectPoolConfig: GenericObjectPoolConfig<*> = GenericObjectPoolConfig<Any?>()
        GenericObjectPoolConfig.maxTotal = 16
        GenericObjectPoolConfig.maxIdle = 8
        GenericObjectPoolConfig.minIdle = 4
        return JedisPoolingClientConfigurationBuilder.poolConfig(GenericObjectPoolConfig).build()
    }


    @Bean
    fun connectionFactory(): JedisConnectionFactory {
        val config = RedisStandaloneConfiguration()
        config.hostName = "localhost"
        config.port = 6379
        config.database =3
        return JedisConnectionFactory(config)
    }


/*    @Bean
    fun redisTemplate(): RedisTemplate<String, Student> {
        val template = RedisTemplate<String, Student>()
        template.setConnectionFactory(connectionFactory())
        template.setEnableTransactionSupport(true)
        template.afterPropertiesSet()

        template.keySerializer = GenericToStringSerializer(Long::class.java)
        template.keySerializer = StringRedisSerializer()
        template.hashKeySerializer = GenericJackson2JsonRedisSerializer()
        template.valueSerializer = GenericJackson2JsonRedisSerializer()
        template.setDefaultSerializer(GenericJackson2JsonRedisSerializer())
        return template

*//*        val redisTemplate = RedisTemplate<String, Student>()
        redisTemplate.setConnectionFactory(connectionFactory())
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = JdkSerializationRedisSerializer()
        redisTemplate.hashKeySerializer = StringRedisSerializer()
        redisTemplate.hashValueSerializer = StringRedisSerializer()
        return redisTemplate*//*

    }*/

    @Bean
    fun redisTemplate(): RedisTemplate<String, String> {
        val redisTemplate = RedisTemplate<String,String>()
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.hashKeySerializer = GenericToStringSerializer(String::class.java)
        redisTemplate.hashValueSerializer = JdkSerializationRedisSerializer()
        redisTemplate.valueSerializer = JdkSerializationRedisSerializer()

        connectionFactory().afterPropertiesSet()
        redisTemplate.setConnectionFactory(connectionFactory())

        return redisTemplate
    }
}