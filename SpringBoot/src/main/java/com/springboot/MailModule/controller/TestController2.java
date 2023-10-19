package com.springboot.MailModule.controller;

import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Validated
@Controller
public class TestController2 {

    /*@ResponseBody
    @PostMapping("/submit")
    public String submit(String username,
                         String password) {

        System.out.println(username.substring(3));
        System.out.println(password.substring(2, 10)); return "请求成功";

    }*/

    /*@ResponseBody
    @PostMapping("/submit")
    public String submit(String username,
                         String password) {

        if (username.length() > 3 && password.length() > 10) {
            System.out.println(username.substring(3));
            System.out.println(password.substring(2, 10)); return "请求成功";
        } else {
            return "请求失败";
        }


    }*/

    @ResponseBody
    @PostMapping("/submit")
    public String submit(@Length(min=3) String username,
                         @Length(min=10) String password) {

        System.out.println(username.substring(3));
        System.out.println(password.substring(2, 10)); return "请求成功";

    }

}
