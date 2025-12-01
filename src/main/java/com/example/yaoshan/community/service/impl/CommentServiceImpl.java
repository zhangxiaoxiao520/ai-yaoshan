package com.example.yaoshan.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.yaoshan.community.dto.CreateCommentDTO;
import com.example.yaoshan.community.dto.PostResponseDTO;
import com.example.yaoshan.community.mapper.CommentMapper;
import com.example.yaoshan.community.mapper.LikeMapper;
import com.example.yaoshan.community.mapper.PostMapper;
import com.example.yaoshan.community.service.CommentService;
import com.example.yaoshan.model.Comment;
import com.example.yaoshan.model.Like;
import com.example.yaoshan.model.Post;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 评论Service实现类
 */
@Service
public class CommentServiceImpl implements CommentService {
    
    @Autowired
    private CommentMapper commentMapper;
    
    @Autowired
    private LikeMapper likeMapper;
    
    @Autowired
    private PostMapper postMapper;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PostResponseDTO.CommentDTO createComment(String openid, CreateCommentDTO createCommentDTO) {
        // 检查帖子是否存在
        Post post = postMapper.selectById(createCommentDTO.getPostId());
        if (post == null || post.getStatus() == 3) {
            return null;
        }
        
        // 如果是回复评论，检查父评论是否存在
        if (createCommentDTO.getParentId() != null && createCommentDTO.getParentId() > 0) {
            Comment parentComment = commentMapper.selectById(createCommentDTO.getParentId());
            if (parentComment == null || !parentComment.getPostId().equals(createCommentDTO.getPostId())) {
                return null;
            }
        }
        
        // 创建评论
        Comment comment = new Comment();
        comment.setPostId(createCommentDTO.getPostId());
        comment.setOpenid(openid);
        comment.setParentId(createCommentDTO.getParentId() != null ? createCommentDTO.getParentId() : 0L);
        comment.setContent(createCommentDTO.getContent());
        comment.setLikeCount(0);
        comment.setStatus(0); // 正常状态
        comment.setCreateTime(new Date());
        comment.setUpdateTime(new Date());
        
        commentMapper.insert(comment);
        
        // 更新帖子评论数
        postMapper.incrementCommentCount(createCommentDTO.getPostId());
        
        return buildCommentDTO(comment, openid);
    }
    
    @Override
    public List<PostResponseDTO.CommentDTO> getPostComments(String openid, Long postId) {
        List<Comment> comments = commentMapper.selectCommentsByPostId(postId);
        
        // 按父评论分组
        Map<Long, List<Comment>> commentMap = comments.stream()
                .filter(comment -> comment.getStatus() == 0) // 只显示正常状态的评论
                .collect(Collectors.groupingBy(Comment::getParentId));
        
        // 构建顶级评论
        List<PostResponseDTO.CommentDTO> result = new ArrayList<>();
        List<Comment> topComments = commentMap.getOrDefault(0L, Collections.emptyList());
        
        for (Comment comment : topComments) {
            PostResponseDTO.CommentDTO commentDTO = buildCommentDTO(comment, openid);
            
            // 添加回复
            List<Comment> replies = commentMap.getOrDefault(comment.getId(), Collections.emptyList());
            if (!replies.isEmpty()) {
                List<PostResponseDTO.CommentDTO> replyDTOs = replies.stream()
                        .map(reply -> buildCommentDTO(reply, openid))
                        .collect(Collectors.toList());
                commentDTO.setReplies(replyDTOs);
            }
            
            result.add(commentDTO);
        }
        
        return result;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteComment(String openid, Long commentId) {
        Comment comment = commentMapper.selectById(commentId);
        if (comment == null || !comment.getOpenid().equals(openid)) {
            return false;
        }
        
        // 软删除
        return commentMapper.updateCommentStatus(commentId, 1) > 0; // 1-删除
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean toggleLike(String openid, Long commentId) {
        // 检查评论是否存在
        Comment comment = commentMapper.selectById(commentId);
        if (comment == null || comment.getStatus() == 1) { // 1-删除
            return false;
        }
        
        // 查询是否已点赞
        Like like = likeMapper.selectByUserAndTarget(openid, commentId, 2); // 2-评论点赞
        
        if (like != null) {
            // 取消点赞
            likeMapper.deleteById(like.getId());
            // 这里应该更新评论点赞数减1，为了简化暂时不加
            return false;
        } else {
            // 添加点赞
            Like newLike = new Like();
            newLike.setOpenid(openid);
            newLike.setTargetId(commentId);
            newLike.setType(2);
            newLike.setCreateTime(new Date());
            likeMapper.insert(newLike);
            
            // 更新评论点赞数
            commentMapper.incrementLikeCount(commentId);
            return true;
        }
    }
    
    @Override
    public int countByOpenid(String openid) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        wrapper.eq("status", 0); // 只统计正常状态的评论
        return commentMapper.selectCount(wrapper).intValue();
    }
    
    /**
     * 构建评论DTO
     */
    private PostResponseDTO.CommentDTO buildCommentDTO(Comment comment, String currentOpenid) {
        PostResponseDTO.CommentDTO commentDTO = new PostResponseDTO.CommentDTO();
        BeanUtils.copyProperties(comment, commentDTO);
        
        // 检查当前用户是否点赞
        if (currentOpenid != null) {
            Like like = likeMapper.selectByUserAndTarget(currentOpenid, comment.getId(), 2); // 2-评论点赞
            commentDTO.setLiked(like != null);
        }
        
        // 暂时设置默认用户信息
        commentDTO.setNickname("用户" + comment.getOpenid().substring(0, 6));
        commentDTO.setAvatar("/images/avatar/default.jpg");
        
        return commentDTO;
    }
}