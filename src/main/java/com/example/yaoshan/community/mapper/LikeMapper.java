package com.example.yaoshan.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.yaoshan.model.Like;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 点赞Mapper接口
 */
@Mapper
public interface LikeMapper extends BaseMapper<Like> {
    
    /**
     * 查询用户是否点赞
     */
    Like selectByUserAndTarget(@Param("openid") String openid, 
                              @Param("targetId") Long targetId, 
                              @Param("type") Integer type);
    
    /**
     * 删除点赞记录
     */
    int deleteByUserAndTarget(@Param("openid") String openid, 
                             @Param("targetId") Long targetId, 
                             @Param("type") Integer type);
    
    /**
     * 根据目标ID和类型统计点赞数
     */
    int countByTargetAndType(@Param("targetId") Long targetId, 
                           @Param("type") Integer type);
    
    /**
     * 根据用户openid统计点赞数
     */
    int countByOpenid(@Param("openid") String openid);
}