package com.example.yaoshan.service.impl;

import com.example.yaoshan.ai.dto.AIRequestDTO;
import com.example.yaoshan.ai.dto.AIResponseDTO;
import com.example.yaoshan.ai.service.impl.AIServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * AI养生咨询服务测试类
 */
@SpringBootTest
class AIServiceImplTest {

    @Autowired
    private AIServiceImpl aiServiceImpl;

    /**
     * 测试AI养生咨询方法
     */
    @Test
    void testConsult() {
        // 创建测试请求
        AIRequestDTO requestDTO = new AIRequestDTO();
        requestDTO.setQuestion("如何改善睡眠质量？");
        requestDTO.setAge(30);
        requestDTO.setGender(1); // 1-男，2-女
        requestDTO.setConstitution("平和质");

        // 调用咨询方法（需要传入openid参数）
        String mockOpenid = "test_openid";
        AIResponseDTO responseDTO = aiServiceImpl.consult(mockOpenid, requestDTO);

        // 验证响应
        assertNotNull(responseDTO);
        assertNotNull(responseDTO.getQuestion());
        assertNotNull(responseDTO.getAnswer());
        assertNotNull(responseDTO.getSolutions());
        assertFalse(responseDTO.getSolutions().isEmpty());
    }

    /**
     * 测试根据体质获取建议方法
     */
    @Test
    void testGetSuggestionByConstitution() {
        String constitutionType = "湿热质";
        String mockOpenid = "test_openid";
        AIResponseDTO responseDTO = aiServiceImpl.getSuggestionByConstitution(mockOpenid, constitutionType);

        assertNotNull(responseDTO);
        assertNotNull(responseDTO.getAnswer());
        assertTrue(responseDTO.getAnswer().contains("湿热质"));
    }

    /**
     * 测试季节性养生建议方法
     */
    @Test
    void testGetSeasonalAdvice() {
        String season = "春"; // 注意：服务端方法使用的是单个季节字，如"春"而不是"春季"
        String mockOpenid = "test_openid";
        AIResponseDTO responseDTO = aiServiceImpl.getSeasonalAdvice(mockOpenid, season);

        assertNotNull(responseDTO);
        assertNotNull(responseDTO.getAnswer());
        assertTrue(responseDTO.getAnswer().contains(season));
    }
}