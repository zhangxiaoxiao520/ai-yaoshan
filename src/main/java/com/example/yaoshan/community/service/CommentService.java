package com.example.yaoshan.community.service;

import com.example.yaoshan.community.dto.CreateCommentDTO;
import com.example.yaoshan.community.dto.PostResponseDTO;
import com.example.yaoshan.model.Comment;

import java.util.List;

/**
 * 评论Service接口
 */
public interface CommentService {
    
    /**
     * 创建评论
     */
    PostResponseDTO.CommentDTO createComment(String openid, CreateCommentDTO createCommentDTO);
    
    /**
     * 获取帖子评论列表
     */
    List<PostResponseDTO.CommentDTO> getPostComments(String openid, Long postId);
    
    /**
     * 删除评论
     */
    boolean deleteComment(String openid, Long commentId);
    
    /**
     * 点赞/取消点赞评论
     */
    boolean toggleLike(String openid, Long commentId);
    
    /**
     * 统计用户评论数
     */
    int countByOpenid(String openid);
}