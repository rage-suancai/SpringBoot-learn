package com.springboot.integrateMybatis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author YXS
 * @PackageName: com.springboot.integrateMybatis.controller
 * @ClassName: MainController
 * @Desription:
 * @date 2023/3/23 17:48
 */
@Controller
public class MainController {

    @ResponseBody
    @RequestMapping("/index")
    public String index() {

        return "Hello Mybatis";

    }

}
