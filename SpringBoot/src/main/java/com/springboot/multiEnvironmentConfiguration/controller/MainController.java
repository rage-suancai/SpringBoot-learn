package com.springboot.multiEnvironmentConfiguration.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author YXS
 * @PackageName: com.springboot.multiEnvironmentConfiguration.controller
 * @ClassName: MainController
 * @Desription:
 * @date 2023/3/25 1:57
 */
@Controller
public class MainController {

    @ResponseBody
    @RequestMapping("/index")
    public String index() {

        return "✅";

    }

}
