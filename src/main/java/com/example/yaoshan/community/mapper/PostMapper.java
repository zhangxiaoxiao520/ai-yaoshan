package com.example.yaoshan.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.yaoshan.model.Post;
import com.example.yaoshan.community.dto.PostQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 帖子Mapper接口
 */
@Mapper
public interface PostMapper extends BaseMapper<Post> {
    
    /**
     * 分页查询帖子列表
     */
    IPage<Post> selectPostList(IPage<Post> page, @Param("query") PostQueryDTO queryDTO);
    
    /**
     * 根据ID查询帖子详情
     */
    Post selectPostById(@Param("id") Long id);
    
    /**
     * 更新帖子状态
     */
    int updatePostStatus(@Param("id") Long id, @Param("status") Integer status);
    
    /**
     * 更新浏览量
     */
    int incrementViewCount(@Param("id") Long id);
    
    /**
     * 更新点赞数
     */
    int incrementLikeCount(@Param("id") Long id);
    
    /**
     * 更新评论数
     */
    int incrementCommentCount(@Param("id") Long id);
}