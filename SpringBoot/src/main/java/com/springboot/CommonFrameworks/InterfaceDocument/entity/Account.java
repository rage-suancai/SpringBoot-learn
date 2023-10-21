package com.springboot.CommonFrameworks.InterfaceDocument.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description="用户信息实体类")
@Data
public class Account {

    @Schema(description="用户编号")
    Integer id;
    @Schema(description="用户名称")
    String name;
    @Schema(description="用户邮箱")
    String email;
    @Schema(description="用户密码")
    String password;

}
