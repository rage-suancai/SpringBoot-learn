package com.SpringBoot.springboot1;

import com.SpringBoot.springboot1.student.Student;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ApplicationTests {

    @Resource
    Student student;

    @Test
    void contextLoads() {
        System.out.println(student);
    }

}
