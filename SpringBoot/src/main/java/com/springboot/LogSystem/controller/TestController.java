/**package com.springboot.LogSystem.controller;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class TestController {

    @PostConstruct
    public void init1() {

        Logger logger = LoggerFactory.getLogger("test");
        logger.info("我是SLF4J日志信息");

    }

    @PostConstruct
    public void init2() {
        log.info("我是SLF4J日志信息");
    }

}**/
