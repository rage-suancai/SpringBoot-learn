package com.springboot.CommonFrameworks.RuleValidation.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class Account {

    @Length(min=3)
    String username;

    @Length(min=10)
    String password;

}
