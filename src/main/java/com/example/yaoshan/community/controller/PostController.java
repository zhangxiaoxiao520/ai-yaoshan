package com.example.yaoshan.community.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.yaoshan.common.Result;
import com.example.yaoshan.community.dto.CreatePostDTO;
import com.example.yaoshan.community.dto.PostQueryDTO;
import com.example.yaoshan.community.dto.PostResponseDTO;
import com.example.yaoshan.community.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * 帖子控制器
 */
@RestController
@RequestMapping("/api/community/post")
public class PostController {
    
    @Autowired
    private PostService postService;
    
    /**
     * 创建帖子
     */
    @PostMapping
    public Result<PostResponseDTO> createPost(@RequestBody CreatePostDTO createPostDTO) {
        String openid = getCurrentUserOpenid();
        PostResponseDTO post = postService.createPost(openid, createPostDTO);
        return Result.success(post);
    }
    
    /**
     * 获取帖子详情
     */
    @GetMapping("/{postId}")
    public Result<PostResponseDTO> getPostDetail(@PathVariable Long postId) {
        String openid = getCurrentUserOpenid();
        PostResponseDTO post = postService.getPostDetail(openid, postId);
        if (post == null) {
            return Result.error("帖子不存在");
        }
        return Result.success(post);
    }
    
    /**
     * 查询帖子列表
     */
    @GetMapping
    public Result<IPage<PostResponseDTO>> getPostList(PostQueryDTO queryDTO) {
        String openid = getCurrentUserOpenid();
        queryDTO.setOpenid(openid);
        IPage<PostResponseDTO> postPage = postService.getPostList(queryDTO);
        return Result.success(postPage);
    }
    
    /**
     * 更新帖子
     */
    @PutMapping("/{postId}")
    public Result<PostResponseDTO> updatePost(@PathVariable Long postId, @RequestBody CreatePostDTO createPostDTO) {
        String openid = getCurrentUserOpenid();
        PostResponseDTO post = postService.updatePost(openid, postId, createPostDTO);
        if (post == null) {
            return Result.error("更新失败，帖子不存在或无权限");
        }
        return Result.success(post);
    }
    
    /**
     * 删除帖子
     */
    @DeleteMapping("/{postId}")
    public Result<Boolean> deletePost(@PathVariable Long postId) {
        String openid = getCurrentUserOpenid();
        boolean success = postService.deletePost(openid, postId);
        if (!success) {
            return Result.error("删除失败，帖子不存在或无权限");
        }
        return Result.success(true);
    }
    
    /**
     * 置顶/取消置顶帖子
     */
    @PutMapping("/{postId}/top")
    public Result<Boolean> setTop(@PathVariable Long postId, @RequestParam boolean isTop) {
        String openid = getCurrentUserOpenid();
        boolean success = postService.setTop(openid, postId, isTop);
        if (!success) {
            return Result.error("操作失败，帖子不存在或无权限");
        }
        return Result.success(true);
    }
    
    /**
     * 点赞/取消点赞帖子
     */
    @PostMapping("/{postId}/like")
    public Result<Boolean> toggleLike(@PathVariable Long postId) {
        String openid = getCurrentUserOpenid();
        boolean isLiked = postService.toggleLike(openid, postId);
        return Result.success(isLiked);
    }
    
    /**
     * 获取当前登录用户的openid
     */
    private String getCurrentUserOpenid() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        throw new RuntimeException("用户未登录");
    }
}