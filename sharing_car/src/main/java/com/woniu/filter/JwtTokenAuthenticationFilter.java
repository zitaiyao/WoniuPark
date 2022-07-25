package com.woniu.filter;

import com.woniu.dao.po.Perm;
import com.woniu.config.MyAuthenticationFailureHandler;
import com.woniu.exception.TokenIsInvalidException;
import com.woniu.dao.mysql.PermMapper;
import com.woniu.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 将用户请求中携带的JWT转化为Authentication Token 存入Spring security上下文COntext
 * 表示每次请求只执行过滤器一次
 */
@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    //用户认证失败的处理器
    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Resource
    private PermMapper permMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        //获取当前请求的uri
        String uri = request.getRequestURI();
        if(uri.endsWith("/login") || uri.endsWith("/login.html")){
            //放行
            filterChain.doFilter(request,response);
            return;
        }
        //不是认证请求 -- 获取申请中的头部token串
        String strToken = request.getHeader("strToken");

        if(StringUtils.isEmpty(strToken)||"null".equals(strToken)){
            //放行
            filterChain.doFilter(request,response);
            return;
        }
        try {
            //检验token是否有效
            if(JwtTokenUtil.checkSign(strToken)){
                //获取token中的用户名
                String username = JwtTokenUtil.getUserId(strToken);
                //查询数据库获取用户的权限集
                List<Perm> perCodes = permMapper.findPermsListByUsername(username);
                List<GrantedAuthority> authorities = new ArrayList<>();
                perCodes.forEach(perCode->{
                    authorities.add(new SimpleGrantedAuthority(perCode.getPercode()));
                });
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(username,"",authorities);
                //存入securityContext
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                //放行
                filterChain.doFilter(request,response);
            }
        }catch (Exception e){
            myAuthenticationFailureHandler.onAuthenticationFailure(request,response,
                    new TokenIsInvalidException("Token无效!"));
        }
    }
}
