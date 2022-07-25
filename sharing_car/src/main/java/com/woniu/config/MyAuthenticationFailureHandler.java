package com.woniu.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.exception.TokenIsInvalidException;
import com.woniu.exception.TokenIsNullException;
import com.woniu.util.ResponseResult;
import com.woniu.util.ResultCode;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        //定义响应的结果对象
        ResponseResult<String> result = null;
        if (e instanceof UsernameNotFoundException) {
            result = ResponseResult.error(ResultCode.USER_ACCOUNT_NOT_EXIST);
        } else if (e instanceof AccountExpiredException) {
            //账号过期
            result = ResponseResult.error(ResultCode.USER_ACCOUNT_EXPIRED);
        } else if (e instanceof BadCredentialsException){
            //凭证不对
            result = ResponseResult.error(ResultCode.USER_CREDENTIALS_ERROR);
        } else if (e instanceof CredentialsExpiredException){
            //密码过期
            result = ResponseResult.error(ResultCode.USER_CREDENTIALS_EXPIRED);
        } else if (e instanceof DisabledException){
            //账号不可用
            result = ResponseResult.error(ResultCode.USER_ACCOUNT_DISABLE);
        } else if (e instanceof LockedException){
            //账号锁定
            result = ResponseResult.error(ResultCode.USER_ACCOUNT_LOCKED);
        } else if (e instanceof TokenIsNullException){
            //token为空
            result = ResponseResult.error(ResultCode.TOKEN_IS_NULL);
        } else if (e instanceof TokenIsInvalidException){
            //token无效
            result = ResponseResult.error(ResultCode.TOKEN_INVALID_EXCEPTION);
        }else {
            result = ResponseResult.error(ResultCode.COMMON_FAIL);
        }
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Origin", "POST,GET");

        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(new ObjectMapper().writeValueAsString(result));//将对象转json输出
        out.flush();
        out.close();
    }
}