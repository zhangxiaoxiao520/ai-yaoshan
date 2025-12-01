package com.example.yaoshan.security.service;

import com.example.yaoshan.model.User;
import com.example.yaoshan.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Spring Security用户详情服务实现
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String openid) throws UsernameNotFoundException {
        // 根据openid查询用户
        User user = userService.getUserByOpenid(openid);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        
        // 构建UserDetails对象
        return new org.springframework.security.core.userdetails.User(
                user.getOpenid(),
                "", // 密码字段，微信登录不需要密码
                new ArrayList<>() // 角色列表，暂时为空
        );
    }
}