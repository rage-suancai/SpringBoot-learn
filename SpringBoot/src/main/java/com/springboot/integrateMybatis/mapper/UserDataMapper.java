package com.springboot.integrateMybatis.mapper;

import com.springboot.integrateMybatis.entity.UserData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author YXS
 * @PackageName: com.springboot.integrateMybatis.mapper
 * @ClassName: MainMapper
 * @Desription:
 * @date 2023/3/24 15:14
 */
@Mapper
public interface UserDataMapper {

    @Select("select id,username,role,password from test_yxs where username = #{username}")
    UserData findUserByName(String username);

}
