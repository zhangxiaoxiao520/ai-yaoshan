package com.example.yaoshan.security.filter;

import com.example.yaoshan.utils.JWTUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT认证过滤器
 */
@Component
public class JWTFilter extends OncePerRequestFilter {

    private JWTUtils jwtUtils;

    public JWTFilter(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 从请求头获取token
        String token = request.getHeader("Authorization");
        
        if (token != null && token.startsWith("Bearer ")) {
            // 移除Bearer前缀
            token = token.substring(7);
            
            try {
                // 验证token并获取openid
                String openid = jwtUtils.getOpenidFromToken(token);
                if (openid != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // 这里可以根据openid从数据库获取用户信息，暂时简化处理
                    // 将用户信息放入SecurityContext
                    UserDetails userDetails = new org.springframework.security.core.userdetails.User(openid, "", null);
                    UsernamePasswordAuthenticationToken authenticationToken = 
                            new UsernamePasswordAuthenticationToken(userDetails, null, null);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (Exception e) {
                // token无效，清除认证信息
                SecurityContextHolder.clearContext();
            }
        }
        
        filterChain.doFilter(request, response);
    }
}