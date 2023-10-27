package com.dataInteraction.MybatisPlusFramework.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dataInteraction.MybatisPlusFramework.entity.Account;
import com.dataInteraction.MybatisPlusFramework.mapper.AccountMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PlusController {

    @Resource
    private AccountMapper accountMapper;

    @ResponseBody
    @GetMapping("/test1")
    public void contextLoads1() {
        System.out.println(accountMapper.selectById(1));
    }

    @ResponseBody
    @GetMapping("/test2")
    public void contextLoads2() {

        /*QueryWrapper<Account> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "小鹿");
        System.out.println(accountMapper.selectOne(wrapper));*/

        /*QueryWrapper<Account> wrapper = Wrappers
                .<Account> query()
                .gt("id", 1)
                .orderByDesc("id");
        System.out.println(accountMapper.selectOne(wrapper));*/

        int count = accountMapper.deleteBatchIds(List.of(1, 3));
        System.out.println("成功删除" + count + "条数据");

    }

    @ResponseBody
    @GetMapping("/test3")
    public void contextLoads3() {

        Page<Account> page = accountMapper.selectPage(Page.of(1, 3), Wrappers.emptyWrapper());
        System.out.println(page.getRecords());



    }

}
