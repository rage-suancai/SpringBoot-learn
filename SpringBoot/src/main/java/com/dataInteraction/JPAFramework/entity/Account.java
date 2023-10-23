package com.dataInteraction.JPAFramework.entity;

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
