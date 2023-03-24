package com.springboot.integrateMybatis.config;

import com.springboot.integrateMybatis.service.UserAuthService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

/**
 * @author YXS
 * @PackageName: com.springboot.integrateMybatis.config
 * @ClassName: SecurityConfiguration
 * @Desription:
 * @date 2023/3/24 17:01
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Resource
    private UserAuthService service;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .userDetailsService(service)
                .passwordEncoder(new BCryptPasswordEncoder());

    }

}
