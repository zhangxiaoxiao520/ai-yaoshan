package com.example.yaoshan.user.service;

import com.example.yaoshan.model.User;
import com.example.yaoshan.user.dto.UpdateUserDTO;

/**
 * 用户服务接口
 */
public interface UserService {
    
    /**
     * 根据openid获取用户
     */
    User getUserByOpenid(String openid);
    
    /**
     * 创建用户
     */
    User createUser(User user);
    
    /**
     * 更新用户信息
     */
    boolean updateUser(String openid, UpdateUserDTO updateUserDTO);
    
    /**
     * 微信登录
     */
    String login(String code);
}