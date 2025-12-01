package com.example.yaoshan.community.controller;

import com.example.yaoshan.common.Result;
import com.example.yaoshan.community.dto.CreateCommentDTO;
import com.example.yaoshan.community.dto.PostResponseDTO;
import com.example.yaoshan.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 评论控制器
 */
@RestController
@RequestMapping("/api/community/comment")
public class CommentController {
    
    @Autowired
    private CommentService commentService;
    
    /**
     * 创建评论
     */
    @PostMapping
    public Result<PostResponseDTO.CommentDTO> createComment(@RequestBody CreateCommentDTO createCommentDTO) {
        String openid = getCurrentUserOpenid();
        PostResponseDTO.CommentDTO comment = commentService.createComment(openid, createCommentDTO);
        if (comment == null) {
            return Result.error("评论失败，帖子不存在或父评论无效");
        }
        return Result.success(comment);
    }
    
    /**
     * 获取帖子的评论列表
     */
    @GetMapping("/post/{postId}")
    public Result<List<PostResponseDTO.CommentDTO>> getPostComments(@PathVariable Long postId) {
        String openid = getCurrentUserOpenid();
        List<PostResponseDTO.CommentDTO> comments = commentService.getPostComments(openid, postId);
        return Result.success(comments);
    }
    
    /**
     * 删除评论
     */
    @DeleteMapping("/{commentId}")
    public Result<Boolean> deleteComment(@PathVariable Long commentId) {
        String openid = getCurrentUserOpenid();
        boolean success = commentService.deleteComment(openid, commentId);
        if (!success) {
            return Result.error("删除失败，评论不存在或无权限");
        }
        return Result.success(true);
    }
    
    /**
     * 点赞/取消点赞评论
     */
    @PostMapping("/{commentId}/like")
    public Result<Boolean> toggleLike(@PathVariable Long commentId) {
        String openid = getCurrentUserOpenid();
        boolean isLiked = commentService.toggleLike(openid, commentId);
        return Result.success(isLiked);
    }
    
    /**
     * 统计用户评论数
     */
    @GetMapping("/count")
    public Result<Integer> countByOpenid() {
        String openid = getCurrentUserOpenid();
        int count = commentService.countByOpenid(openid);
        return Result.success(count);
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