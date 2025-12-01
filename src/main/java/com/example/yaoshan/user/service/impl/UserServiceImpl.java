package com.example.yaoshan.user.service.impl;

import com.example.yaoshan.common.BusinessException;
import com.example.yaoshan.model.User;
import com.example.yaoshan.user.dto.UpdateUserDTO;
import com.example.yaoshan.user.mapper.UserMapper;
import com.example.yaoshan.user.service.UserService;
import com.example.yaoshan.utils.JWTUtils;
import com.example.yaoshan.utils.WechatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private JWTUtils jwtUtils;
    
    @Autowired
    private WechatUtils wechatUtils;
    
    @Override
    public User getUserByOpenid(String openid) {
        return userMapper.selectByOpenid(openid);
    }
    
    @Override
    public User createUser(User user) {
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        userMapper.insert(user);
        return user;
    }
    
    @Override
    public boolean updateUser(String openid, UpdateUserDTO updateUserDTO) {
        User user = getUserByOpenid(openid);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        
        // 复制属性
        BeanUtils.copyProperties(updateUserDTO, user);
        user.setUpdateTime(new Date());
        
        return userMapper.updateById(user) > 0;
    }
    
    @Override
    public String login(String code) {
        // 调用微信接口获取openid
        String openid = wechatUtils.wxLogin(code);
        
        // 查询用户是否存在
        User user = getUserByOpenid(openid);
        if (user == null) {
            // 创建新用户
            user = new User();
            user.setOpenid(openid);
            createUser(user);
        }
        
        // 生成token
        return jwtUtils.generateToken(openid);
    }
}