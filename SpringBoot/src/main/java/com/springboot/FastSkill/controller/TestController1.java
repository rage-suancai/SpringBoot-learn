/**package com.springboot.FastSkill.controller;

import com.springboot.FastSkill.entity.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController1 {

    @ResponseBody
    @GetMapping("/index1")
    public String index1() {
        return "Fuck World";
    }

    @ResponseBody
    @GetMapping("/index2")
    public Student index2() {

        Student student = new Student();
        student.setId(88); student.setName("麦克"); student.setSex("58");
        return student;

    }

//    @GetMapping("/login")
//    public String login() {
//        return "login";
//    }

}**/
