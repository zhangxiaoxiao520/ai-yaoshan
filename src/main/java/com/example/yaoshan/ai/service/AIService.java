package com.example.yaoshan.ai.service;

import com.example.yaoshan.ai.dto.AIRequestDTO;
import com.example.yaoshan.ai.dto.AIResponseDTO;

/**
 * AI养生咨询服务接口
 */
public interface AIService {
    
    /**
     * 进行AI养生咨询
     * @param openid 用户openid
     * @param requestDTO 咨询请求数据
     * @return AI咨询响应结果
     */
    AIResponseDTO consult(String openid, AIRequestDTO requestDTO);
    
    /**
     * 根据体质获取养生建议
     * @param openid 用户openid
     * @param constitution 体质类型
     * @return AI咨询响应结果
     */
    AIResponseDTO getSuggestionByConstitution(String openid, String constitution);
    
    /**
     * 获取季节性养生建议
     * @param openid 用户openid
     * @param season 季节（春、夏、秋、冬）
     * @return AI咨询响应结果
     */
    AIResponseDTO getSeasonalAdvice(String openid, String season);
    
    /**
     * 获取饮食养生建议
     * @param openid 用户openid
     * @param foodType 食物类型或名称
     * @return AI咨询响应结果
     */
    AIResponseDTO getFoodAdvice(String openid, String foodType);
    
    /**
     * 获取运动养生建议
     * @param openid 用户openid
     * @param age 用户年龄
     * @param healthCondition 健康状况
     * @return AI咨询响应结果
     */
    AIResponseDTO getExerciseAdvice(String openid, Integer age, String healthCondition);
}