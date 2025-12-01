package com.example.yaoshan.ai.dto;

import lombok.Data;

import java.util.List;

/**
 * AI养生咨询请求DTO
 */
@Data
public class AIRequestDTO {
    
    /**
     * 用户问题
     */
    private String question;
    
    /**
     * 用户症状描述
     */
    private String symptoms;
    
    /**
     * 用户年龄
     */
    private Integer age;
    
    /**
     * 用户性别（1-男，2-女）
     */
    private Integer gender;
    
    /**
     * 用户体质类型（如：湿热质、阴虚质等）
     */
    private String constitution;
    
    /**
     * 用户饮食偏好
     */
    private String dietPreference;
    
    /**
     * 用户健康问题列表
     */
    private List<String> healthIssues;
    
    /**
     * 用户期望的养生方式（食疗、运动、按摩等）
     */
    private String expectMethod;
}