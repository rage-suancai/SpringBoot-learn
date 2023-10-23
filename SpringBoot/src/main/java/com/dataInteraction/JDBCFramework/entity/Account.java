package com.dataInteraction.JDBCFramework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Account {

    Integer id;
    String name;
    String email;
    String password;

}
