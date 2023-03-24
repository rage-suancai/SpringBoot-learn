package com.springboot.logSystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author YXS
 * @PackageName: com.springboot.logSystem.controller
 * @ClassName: MainController
 * @Desription:
 * @date 2023/3/24 23:26
 */
//@Slf4j
/*@Controller
public class MainController {

    @ResponseBody
    @RequestMapping("/index1")
    public String index1() {

        Logger logger = LoggerFactory.getLogger(MainController.class);
        logger.info("用户访问了一次页面");
        return "Hello slf4";

    }

    @ResponseBody
    @RequestMapping("/index2")
    public String index2(HttpServletRequest request) {

        Logger logger = LoggerFactory.getLogger(MainController.class);
        MDC.put("reqID", request.getSession().getId());

        logger.info("用户访问了一次页面");
        return "Hello Logback";

    }

}*/
