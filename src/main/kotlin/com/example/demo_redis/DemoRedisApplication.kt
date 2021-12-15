package com.example.demo_redis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class DemoRedisApplication

fun main(args: Array<String>) {
	runApplication<DemoRedisApplication>(*args)
}
