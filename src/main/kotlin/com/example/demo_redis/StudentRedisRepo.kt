package com.example.demo_redis

import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class StudentRedisRepo {

    //@Qualifier("redisTemplate")
    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String,String>
    private val hash_key = "Student"

    @Autowired
    var gson = Gson()


    fun save(student: Student):Student{
        redisTemplate.opsForHash<String,String>().put(hash_key,student.id.toString(),gson.toJson(student))
        return student
    }

    fun all():MutableList<Student>{
        val rs = mutableListOf<Student>()
        redisTemplate.opsForHash<String,String?>().values(hash_key).forEach {
            rs.add(gson.fromJson(it,Student::class.java))
        }

        return rs
    }
}