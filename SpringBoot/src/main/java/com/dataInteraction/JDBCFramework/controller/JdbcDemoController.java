/**package com.dataInteraction.JDBCFramework.controller;

import com.dataInteraction.JDBCFramework.service.JdbcDemoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JdbcDemoController {

    @Resource
    private JdbcDemoService jdbcDemoService;

    @ResponseBody
    @GetMapping("/test1")
    public String contextLoads1() {
        jdbcDemoService.contextLoads1(); return "查询成功";
    }

    @ResponseBody
    @GetMapping("/test2")
    public String contextLoads2() {
        jdbcDemoService.contextLoads2(); return "查询成功";
    }

    @ResponseBody
    @GetMapping("/test3")
    public String contextLoads3() {
        jdbcDemoService.contextLoads3(); return "新增成功";
    }

    @ResponseBody
    @GetMapping("/test4")
    public String contextLoads4() {
        jdbcDemoService.contextLoads4(); return "新增成功";
    }

}**/
