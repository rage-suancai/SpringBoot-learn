/**package com.springboot.FastSkill.controller;

import com.springboot.FastSkill.entity.Account;
import com.springboot.FastSkill.mapper.AccountMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController2 {

//    @Value("${test.data}")
//    String data;

    @Resource
    private AccountMapper accountMapper;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @ResponseBody
    @GetMapping("/account")
    public Account account() {
        return accountMapper.findUserById(1);
    }

}**/
