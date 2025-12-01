package com.example.yaoshan.utils;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信工具类，处理微信小程序相关功能
 */
@Component
public class WechatUtils {
    
    @Value("${wechat.appid}")
    private String appid;
    
    @Value("${wechat.secret}")
    private String secret;
    
    private final RestTemplate restTemplate = new RestTemplate();
    
    /**
     * 微信登录，获取openid
     */
    public String wxLogin(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        
        Map<String, String> params = new HashMap<>();
        params.put("appid", appid);
        params.put("secret", secret);
        params.put("js_code", code);
        params.put("grant_type", "authorization_code");
        
        // 发送请求到微信服务器
        String result = restTemplate.getForObject(url + "?appid={appid}&secret={secret}&js_code={js_code}&grant_type={grant_type}", String.class, params);
        
        // 解析返回结果
        JSONObject jsonObject = JSONObject.parseObject(result);
        
        // 检查是否有错误
        if (jsonObject.containsKey("errcode")) {
            throw new RuntimeException("微信登录失败：" + jsonObject.getString("errmsg"));
        }
        
        // 返回openid
        return jsonObject.getString("openid");
    }
}