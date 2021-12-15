package com.example.demo_redis

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/student")
class StudentController {

    @Autowired
    lateinit var studentRedisRepo: StudentRedisRepo


    @GetMapping("/all")
    fun getAll() : MutableList<Student> {
        return studentRedisRepo.all()
    }


    @PostMapping
    fun save(@RequestBody student: Student): Student {
         return studentRedisRepo.save(student)
    }
}