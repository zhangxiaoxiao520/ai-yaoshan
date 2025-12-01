package com.example.yaoshan.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.yaoshan.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评论Mapper接口
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    
    /**
     * 根据帖子ID查询评论列表
     */
    List<Comment> selectCommentsByPostId(@Param("postId") Long postId);
    
    /**
     * 根据父评论ID查询回复列表
     */
    List<Comment> selectRepliesByParentId(@Param("parentId") Long parentId);
    
    /**
     * 更新评论状态
     */
    int updateCommentStatus(@Param("id") Long id, @Param("status") Integer status);
    
    /**
     * 更新点赞数
     */
    int incrementLikeCount(@Param("id") Long id);
    
    /**
     * 根据用户openid查询评论数
     */
    int countByOpenid(@Param("openid") String openid);
}