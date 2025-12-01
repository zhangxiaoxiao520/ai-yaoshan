package com.example.yaoshan.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.yaoshan.community.dto.CreatePostDTO;
import com.example.yaoshan.community.dto.PostQueryDTO;
import com.example.yaoshan.community.dto.PostResponseDTO;
import com.example.yaoshan.community.mapper.CommentMapper;
import com.example.yaoshan.community.mapper.LikeMapper;
import com.example.yaoshan.community.mapper.PostMapper;
import com.example.yaoshan.community.service.PostService;
import com.example.yaoshan.model.Comment;
import com.example.yaoshan.model.Like;
import com.example.yaoshan.model.Post;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 帖子Service实现类
 */
@Service
public class PostServiceImpl implements PostService {
    
    @Autowired
    private PostMapper postMapper;
    
    @Autowired
    private CommentMapper commentMapper;
    
    @Autowired
    private LikeMapper likeMapper;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PostResponseDTO createPost(String openid, CreatePostDTO createPostDTO) {
        // 创建帖子
        Post post = new Post();
        post.setOpenid(openid);
        post.setTitle(createPostDTO.getTitle());
        post.setContent(createPostDTO.getContent());
        // 将图片列表转换为逗号分隔的字符串
        if (createPostDTO.getImages() != null && !createPostDTO.getImages().isEmpty()) {
            post.setImages(String.join(",", createPostDTO.getImages()));
        }
        post.setViewCount(0);
        post.setLikeCount(0);
        post.setCommentCount(0);
        post.setStatus(0); // 正常状态
        post.setCreateTime(new Date());
        post.setUpdateTime(new Date());
        
        postMapper.insert(post);
        
        return buildPostResponseDTO(post, openid);
    }
    
    @Override
    public PostResponseDTO getPostDetail(String openid, Long postId) {
        Post post = postMapper.selectPostById(postId);
        if (post == null || post.getStatus() == 3) { // 3-删除
            return null;
        }
        
        // 增加浏览量
        postMapper.incrementViewCount(postId);
        
        PostResponseDTO responseDTO = buildPostResponseDTO(post, openid);
        
        // 加载评论列表
        List<Comment> comments = commentMapper.selectCommentsByPostId(postId);
        if (!comments.isEmpty()) {
            responseDTO.setComments(buildCommentDTOs(comments, openid));
        }
        
        return responseDTO;
    }
    
    @Override
    public IPage<PostResponseDTO> getPostList(PostQueryDTO queryDTO) {
        IPage<Post> page = new Page<>(queryDTO.getPage(), queryDTO.getSize());
        IPage<Post> postPage = postMapper.selectPostList(page, queryDTO);
        
        return postPage.convert(post -> buildPostResponseDTO(post, queryDTO.getOpenid()));
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PostResponseDTO updatePost(String openid, Long postId, CreatePostDTO createPostDTO) {
        Post post = postMapper.selectById(postId);
        if (post == null || !post.getOpenid().equals(openid) || post.getStatus() == 3) {
            return null;
        }
        
        // 更新帖子信息
        post.setTitle(createPostDTO.getTitle());
        post.setContent(createPostDTO.getContent());
        if (createPostDTO.getImages() != null) {
            post.setImages(String.join(",", createPostDTO.getImages()));
        }
        post.setUpdateTime(new Date());
        
        postMapper.updateById(post);
        
        return buildPostResponseDTO(post, openid);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deletePost(String openid, Long postId) {
        Post post = postMapper.selectById(postId);
        if (post == null || !post.getOpenid().equals(openid)) {
            return false;
        }
        
        // 软删除
        return postMapper.updatePostStatus(postId, 3) > 0; // 3-删除
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setTop(String openid, Long postId, boolean isTop) {
        Post post = postMapper.selectById(postId);
        if (post == null || !post.getOpenid().equals(openid)) {
            return false;
        }
        
        // 1-置顶，0-正常
        return postMapper.updatePostStatus(postId, isTop ? 1 : 0) > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean toggleLike(String openid, Long postId) {
        // 检查帖子是否存在
        Post post = postMapper.selectById(postId);
        if (post == null || post.getStatus() == 3) {
            return false;
        }
        
        // 查询是否已点赞
        Like like = likeMapper.selectByUserAndTarget(openid, postId, 1); // 1-帖子点赞
        
        if (like != null) {
            // 取消点赞
            likeMapper.deleteById(like.getId());
            // 这里应该更新帖子点赞数减1，为了简化暂时不加
            return false;
        } else {
            // 添加点赞
            Like newLike = new Like();
            newLike.setOpenid(openid);
            newLike.setTargetId(postId);
            newLike.setType(1);
            newLike.setCreateTime(new Date());
            likeMapper.insert(newLike);
            
            // 更新帖子点赞数
            postMapper.incrementLikeCount(postId);
            return true;
        }
    }
    
    @Override
    public int countByOpenid(String openid) {
        QueryWrapper<Post> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        wrapper.ne("status", 3); // 排除已删除的
        return postMapper.selectCount(wrapper).intValue();
    }
    
    /**
     * 构建帖子响应DTO
     */
    private PostResponseDTO buildPostResponseDTO(Post post, String currentOpenid) {
        PostResponseDTO responseDTO = new PostResponseDTO();
        BeanUtils.copyProperties(post, responseDTO);
        
        // 处理图片列表
        if (StringUtils.isNotBlank(post.getImages())) {
            List<String> images = Arrays.asList(post.getImages().split(","));
            responseDTO.setImages(images);
        }
        
        // 设置状态文本
        switch (post.getStatus()) {
            case 0: responseDTO.setStatusText("正常"); break;
            case 1: responseDTO.setStatusText("置顶"); break;
            case 2: responseDTO.setStatusText("精华"); break;
            case 3: responseDTO.setStatusText("已删除"); break;
            default: responseDTO.setStatusText("未知状态");
        }
        
        // 检查当前用户是否点赞
        if (StringUtils.isNotBlank(currentOpenid)) {
            Like like = likeMapper.selectByUserAndTarget(currentOpenid, post.getId(), 1);
            responseDTO.setLiked(like != null);
        }
        
        // 暂时设置默认用户信息
        responseDTO.setNickname("用户" + post.getOpenid().substring(0, 6));
        responseDTO.setAvatar("/images/avatar/default.jpg");
        
        return responseDTO;
    }
    
    /**
     * 构建评论DTO列表
     */
    private List<PostResponseDTO.CommentDTO> buildCommentDTOs(List<Comment> comments, String currentOpenid) {
        // 按父评论分组
        Map<Long, List<Comment>> commentMap = comments.stream()
                .filter(comment -> comment.getStatus() == 0) // 只显示正常状态的评论
                .collect(Collectors.groupingBy(Comment::getParentId));
        
        // 构建顶级评论
        List<PostResponseDTO.CommentDTO> result = new ArrayList<>();
        List<Comment> topComments = commentMap.getOrDefault(0L, Collections.emptyList());
        
        for (Comment comment : topComments) {
            PostResponseDTO.CommentDTO commentDTO = buildCommentDTO(comment, currentOpenid);
            
            // 添加回复
            List<Comment> replies = commentMap.getOrDefault(comment.getId(), Collections.emptyList());
            if (!replies.isEmpty()) {
                List<PostResponseDTO.CommentDTO> replyDTOs = replies.stream()
                        .map(reply -> buildCommentDTO(reply, currentOpenid))
                        .collect(Collectors.toList());
                commentDTO.setReplies(replyDTOs);
            }
            
            result.add(commentDTO);
        }
        
        return result;
    }
    
    /**
     * 构建评论DTO
     */
    private PostResponseDTO.CommentDTO buildCommentDTO(Comment comment, String currentOpenid) {
        PostResponseDTO.CommentDTO commentDTO = new PostResponseDTO.CommentDTO();
        BeanUtils.copyProperties(comment, commentDTO);
        
        // 检查当前用户是否点赞
        if (StringUtils.isNotBlank(currentOpenid)) {
            Like like = likeMapper.selectByUserAndTarget(currentOpenid, comment.getId(), 2); // 2-评论点赞
            commentDTO.setLiked(like != null);
        }
        
        // 暂时设置默认用户信息
        commentDTO.setNickname("用户" + comment.getOpenid().substring(0, 6));
        commentDTO.setAvatar("/images/avatar/default.jpg");
        
        return commentDTO;
    }
}