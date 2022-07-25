package com.woniu.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.dao.mysql.AccountMapper;
import com.woniu.util.JwtTokenUtil;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        try {
            //获取当前登录认证成功的用户名
            String username = request.getParameter("username");
            String strToken = JwtTokenUtil.createSign(username);

            //通过响应的json返回客户端
            ResponseResult<String> result = new ResponseResult<>(strToken,"OK",200);
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.write(new ObjectMapper().writeValueAsString(result));
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
