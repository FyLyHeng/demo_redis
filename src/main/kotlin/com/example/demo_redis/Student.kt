package com.example.demo_redis

import org.springframework.data.redis.core.RedisHash
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
@RedisHash("Student")
data class Student (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long?=null,
        var name: String? = "",
        var gender: String = "M"
)