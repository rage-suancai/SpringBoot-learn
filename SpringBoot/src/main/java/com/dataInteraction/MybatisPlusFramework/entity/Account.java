package com.dataInteraction.MybatisPlusFramework.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("db_account")
@Data
public class Account {

    @TableId(type = IdType.AUTO)
    Integer id;

    @TableField
    String name;

    @TableField
    String email;

    @TableField
    String password;

}
