/**package com.springboot.CommonFrameworks.InterfaceDocument.Controller;

import com.springboot.CommonFrameworks.InterfaceDocument.entity.Account;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Tag(name="接口文档测试验证相关", description="包括接口, 联调, 请求验证等操作")
@Controller
public class SwaggerController {

    @ApiResponses({
            @ApiResponse(responseCode="200", description="测试成功"),
            @ApiResponse(responseCode="500", description="测试失败")
    })
    @Operation(summary="请求用户数据测试接口")
    @ResponseBody
    @GetMapping("/swaggerTest")
    public String SwaggerTest1(@Parameter(description="测试文本数据", example="KFC vivo 50") @RequestParam String text) {
        return "Hello Swagger";
    }

    @ApiResponses({
            @ApiResponse(responseCode="200", description="测试成功"),
            @ApiResponse(responseCode="500", description="测试失败")
    })
    @Operation(description="用户登录接口", summary="请求用户数据测试接口")
    @ResponseBody
    @PostMapping("/swaggerTest")
    public Account SwaggerTest2(@Parameter(description="用户名", example="KFC vivo 50") @RequestParam String name,
                                @Parameter(description="密码", example="KFC vivo 50") @RequestParam String password) {
        return new Account();
    }

    @Hidden
    @ResponseBody
    @GetMapping("/hidden")
    public String hidden() {
        return "Hello Swagger";
    }

}**/
