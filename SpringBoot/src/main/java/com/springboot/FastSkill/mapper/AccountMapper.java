package com.springboot.FastSkill.mapper;

import com.springboot.FastSkill.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AccountMapper {

    @Select("select id,name,password,email from db_user where id = #{id}")
    Account findUserById(Integer id);

}
