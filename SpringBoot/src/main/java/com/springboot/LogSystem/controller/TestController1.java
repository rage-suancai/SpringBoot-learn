package com.springboot.LogSystem.controller;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class TestController1 {

//    @PostConstruct
//    public void init() {
//
//        Logger logger = LoggerFactory.getLogger("test");
//        logger.info("我是SLF4J日志信息");
//
//    }

    @PostConstruct
    public void init() {
        log.info("我是SLF4J日志信息");
    }

}
