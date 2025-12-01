package com.example.yaoshan.user.controller;

import com.example.yaoshan.common.Result;
import com.example.yaoshan.model.User;
import com.example.yaoshan.user.dto.LoginDTO;
import com.example.yaoshan.user.dto.UpdateUserDTO;
import com.example.yaoshan.user.service.UserService;
import com.example.yaoshan.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JWTUtils jwtUtils;
    
    /**
     * 微信登录
     */
    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginDTO loginDTO) {
        String token = userService.login(loginDTO.getCode());
        return Result.success(token);
    }
    
    /**
     * 获取用户信息
     */
    @GetMapping("/info")
    public Result<User> getUserInfo(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        
        String openid = jwtUtils.getOpenidFromToken(token);
        if (openid == null) {
            return Result.error(401, "未授权");
        }
        
        User user = userService.getUserByOpenid(openid);
        return Result.success(user);
    }
    
    /**
     * 更新用户信息
     */
    @PutMapping("/update")
    public Result<Boolean> updateUser(@RequestBody UpdateUserDTO updateUserDTO, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        
        String openid = jwtUtils.getOpenidFromToken(token);
        if (openid == null) {
            return Result.error(401, "未授权");
        }
        
        boolean result = userService.updateUser(openid, updateUserDTO);
        return Result.success(result);
    }
}