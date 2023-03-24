package com.springboot.integrateMybatis.service;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author YXS
 * @PackageName: com.springboot.integrateMybatis.service
 * @ClassName: UserAuthService
 * @Desription:
 * @date 2023/3/24 15:46
 */
/*@Service
public class UserAuthService implements UserDetailsService {

    @Resource
    private UserDataMapper dataMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserData data = dataMapper.findUserByName(username);
        if (data == null) throw new UsernameNotFoundException("用户 " + username + "登录失败 用户名不存在");
        return User
                .withUsername(data.getUsername())
                .password(data.getPassword())
                .roles(data.getRole())
                .build();

    }

}*/
