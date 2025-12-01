package com.example.yaoshan.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.yaoshan.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper接口
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    
    /**
     * 根据openid查询用户
     */
    User selectByOpenid(String openid);
}