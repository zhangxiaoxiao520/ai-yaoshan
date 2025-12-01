package com.example.yaoshan.community.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.yaoshan.community.dto.CreatePostDTO;
import com.example.yaoshan.community.dto.PostQueryDTO;
import com.example.yaoshan.community.dto.PostResponseDTO;
import com.example.yaoshan.model.Post;

/**
 * 帖子Service接口
 */
public interface PostService {
    
    /**
     * 创建帖子
     */
    PostResponseDTO createPost(String openid, CreatePostDTO createPostDTO);
    
    /**
     * 获取帖子详情
     */
    PostResponseDTO getPostDetail(String openid, Long postId);
    
    /**
     * 查询帖子列表
     */
    IPage<PostResponseDTO> getPostList(PostQueryDTO queryDTO);
    
    /**
     * 更新帖子
     */
    PostResponseDTO updatePost(String openid, Long postId, CreatePostDTO createPostDTO);
    
    /**
     * 删除帖子
     */
    boolean deletePost(String openid, Long postId);
    
    /**
     * 置顶/取消置顶帖子
     */
    boolean setTop(String openid, Long postId, boolean isTop);
    
    /**
     * 点赞/取消点赞帖子
     */
    boolean toggleLike(String openid, Long postId);
    
    /**
     * 统计用户发帖数
     */
    int countByOpenid(String openid);
}