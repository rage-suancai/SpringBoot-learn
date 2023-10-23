package com.dataInteraction.JDBCFramework.service.impl;

import com.dataInteraction.JDBCFramework.entity.Account;
import com.dataInteraction.JDBCFramework.service.JdbcDemoService;
import jakarta.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Service
public class JdbcDemoServiceImpl implements JdbcDemoService {

    @Resource
    private JdbcTemplate template;
    @Resource
    private DataSource dataSource;

    @Override
    public void contextLoads1() {

        Map<String, Object> map = template.queryForMap("select * from db_user where id = ?", 1);
        System.out.println(map);

    }

    @Override
    public void contextLoads2() {

        Account account = template.queryForObject("select * from db_user where id = ?",
                (r, i) -> new Account(r.getInt(1), r.getString(2),
                                      r.getString(3), r.getString(4)), 1);
        System.out.println(account);

    }

    @Override
    public void contextLoads3() {

        int update = template.update("insert into db_user values(1, 'admin', '911@qq.com', '123456')");
        System.out.println("更新了: " + update + " 行");

    }

    @Override
    public void contextLoads4() {

        SimpleJdbcInsert simple = new SimpleJdbcInsert(dataSource)
                .withTableName("user")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> user = new HashMap<>(2);
        user.put("name", "bod"); user.put("email", "911@qq.com"); user.put("password", "123456");
        Number number = simple.executeAndReturnKey(user);

        System.out.println(number);

    }

}
