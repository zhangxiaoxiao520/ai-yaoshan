package com.example.yaoshan.security.handler;

import com.example.yaoshan.common.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 认证异常处理器
 */
@Component
public class AuthenticationExceptionHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 设置响应状态码
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // 设置响应内容类型
        response.setContentType("application/json;charset=UTF-8");
        // 构建响应数据
        Result<String> result = Result.error(401, "未授权访问，请先登录");
        
        // 输出响应
        try (PrintWriter out = response.getWriter()) {
            out.write(new ObjectMapper().writeValueAsString(result));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}