package com.dataInteraction.MybatisPlusFramework.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dataInteraction.MybatisPlusFramework.entity.Account;
import com.dataInteraction.MybatisPlusFramework.mapper.AccountMapper;
import com.dataInteraction.MybatisPlusFramework.service.PlusService;
import org.springframework.stereotype.Service;

@Service
public class PlusServiceImpl extends ServiceImpl<AccountMapper, Account> implements PlusService {



}
