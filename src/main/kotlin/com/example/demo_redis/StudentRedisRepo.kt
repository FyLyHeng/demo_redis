package com.example.demo_redis

import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class StudentRedisRepo {

    @Qualifier("redisTemplates")
    @Autowired
    lateinit var redisTemplate: RedisTemplate<String,String>
    private val hash_key = "Custom:${Student::class.java.simpleName}"

    @Autowired
    var gson = Gson()


    fun save(student: Student):Student{
        redisTemplate.opsForHash<String,String>().put(hash_key,student.id.toString(),gson.toJson(student))
        return student
    }

    fun all():MutableList<Student>{
        val rs = mutableListOf<Student>()

        //list all value
        redisTemplate.opsForHash<String,String?>().values(hash_key).forEach {
            rs.add(gson.fromJson(it,Student::class.java))
        }



        //list all key and value
        redisTemplate.opsForHash<String,String>().entries(hash_key).forEach { t, u ->
            println("Key {$t}  value : $u")
        }


        //get by id
        redisTemplate.opsForHash<String,String>().get(hash_key,1).let {
            println(it)
        }


        //get multi by ids
        redisTemplate.opsForHash<Long,String>().multiGet(hash_key, listOf(1,2)).forEach {
            println(it)
        }


        return rs
    }
}