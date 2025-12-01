package com.example.yaoshan.ai.dto;

import lombok.Data;

import java.util.List;

/**
 * AI养生咨询响应DTO
 */
@Data
public class AIResponseDTO {
    
    /**
     * 咨询问题
     */
    private String question;
    
    /**
     * AI回复内容
     */
    private String answer;
    
    /**
     * 推荐的养生方案列表
     */
    private List<HealthSolutionDTO> solutions;
    
    /**
     * 推荐的养生食材
     */
    private List<FoodDTO> recommendedFoods;
    
    /**
     * 注意事项
     */
    private List<String> notices;
    
    /**
     * 咨询时间
     */
    private String consultTime;
    
    /**
     * 健康建议摘要
     */
    private String summary;
    
    /**
     * 养生方案DTO
     */
    @Data
    public static class HealthSolutionDTO {
        
        /**
         * 方案标题
         */
        private String title;
        
        /**
         * 方案描述
         */
        private String description;
        
        /**
         * 方案步骤
         */
        private List<String> steps;
        
        /**
         * 推荐指数（1-5）
         */
        private Integer recommendLevel;
    }
    
    /**
     * 食材DTO
     */
    @Data
    public static class FoodDTO {
        
        /**
         * 食材名称
         */
        private String name;
        
        /**
         * 食材功效
         */
        private String effect;
        
        /**
         * 食用建议
         */
        private String suggestion;
        
        /**
         * 禁忌
         */
        private String taboos;
    }
}