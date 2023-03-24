package com.springboot.integrateMybatis.entity;

import lombok.Data;

/**
 * @author YXS
 * @PackageName: com.springboot.integrateMybatis.entity
 * @ClassName: UserData
 * @Desription:
 * @date 2023/3/24 15:16
 */
@Data
public class UserData {

    Integer id;
    String username;
    String role;
    String password;

}
