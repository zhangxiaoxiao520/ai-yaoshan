package com.example.yaoshan.ai.controller;

import com.example.yaoshan.common.Result;
import com.example.yaoshan.ai.dto.AIRequestDTO;
import com.example.yaoshan.ai.dto.AIResponseDTO;
import com.example.yaoshan.ai.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * AI养生咨询控制器
 */
@RestController
@RequestMapping("/api/ai/health")
public class AIController {
    
    @Autowired
    private AIService aiService;
    
    /**
     * 进行AI养生咨询
     */
    @PostMapping("/consult")
    public Result<AIResponseDTO> consult(@RequestBody AIRequestDTO requestDTO) {
        String openid = getCurrentUserOpenid();
        AIResponseDTO response = aiService.consult(openid, requestDTO);
        return Result.success(response);
    }
    
    /**
     * 根据体质获取养生建议
     */
    @GetMapping("/constitution/{constitution}")
    public Result<AIResponseDTO> getSuggestionByConstitution(@PathVariable String constitution) {
        String openid = getCurrentUserOpenid();
        AIResponseDTO response = aiService.getSuggestionByConstitution(openid, constitution);
        return Result.success(response);
    }
    
    /**
     * 获取季节性养生建议
     */
    @GetMapping("/season/{season}")
    public Result<AIResponseDTO> getSeasonalAdvice(@PathVariable String season) {
        String openid = getCurrentUserOpenid();
        AIResponseDTO response = aiService.getSeasonalAdvice(openid, season);
        return Result.success(response);
    }
    
    /**
     * 获取饮食养生建议
     */
    @GetMapping("/food/{foodType}")
    public Result<AIResponseDTO> getFoodAdvice(@PathVariable String foodType) {
        String openid = getCurrentUserOpenid();
        AIResponseDTO response = aiService.getFoodAdvice(openid, foodType);
        return Result.success(response);
    }
    
    /**
     * 获取运动养生建议
     */
    @GetMapping("/exercise")
    public Result<AIResponseDTO> getExerciseAdvice(@RequestParam Integer age, @RequestParam String healthCondition) {
        String openid = getCurrentUserOpenid();
        AIResponseDTO response = aiService.getExerciseAdvice(openid, age, healthCondition);
        return Result.success(response);
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