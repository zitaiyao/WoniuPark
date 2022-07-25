package com.woniu.config;

import com.woniu.filter.JwtTokenAuthenticationFilter;
import com.woniu.service.impl.AccountServiceImpl;
import com.woniu.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(jsr250Enabled = true)//开启security注解鉴权
//@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)//springSecurity自带 可以支持spring
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Autowired
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;

    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;

    @Autowired
    private JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;

    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;

    @Bean
    public DaoAuthenticationProvider getDaoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        //设置用户未找到异常不隐藏
        provider.setHideUserNotFoundExceptions(false);
        //设置认证管理器使用UserDetailsService对象--此处使用的使我们真的有的业务实现类
        provider.setUserDetailsService(accountService);
        //设置认证管理器使用的面检验器对象
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //指定认证管理器
        auth.authenticationProvider(getDaoAuthenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //开启自定义表单模式登录认证
        //需要放行的url在这里配置,必须要放行/login和/login.html,不然会报错
        http.authorizeRequests().antMatchers("/login","/login.html","/swagger-ui/index.html")
                .permitAll().anyRequest().authenticated()
                .and().
                //设置登录页、登录表单form中action的地址,也就是处理认证请求的路径
        formLogin().loginPage("/login.html").loginProcessingUrl("/login")
                //登录表单form中面输入框input的name名,不修改的话默认是password
                .usernameParameter("username").passwordParameter("password")
                //登录认证成功后默认跳转的路径
                //.defaultSuccessUrl("/home")
                //前后端分离认证成功的处理器--输出json
                .successHandler(myAuthenticationSuccessHandler)
                        //登录认证失败后请求URL,要放行
                        //此处不能指定/error,因为这个/error是security内置的请求处理
                //前后端分离认证失败的处理器--输出json
                                .failureHandler(myAuthenticationFailureHandler)
                                        .and()
                                                //前后端分离处理未登录请求
                                                .exceptionHandling()
                                                        .authenticationEntryPoint(myAuthenticationEntryPoint)
                //前后端分离处理权限不足的请求
                .accessDeniedHandler(myAccessDeniedHandler)
                //前后端分离处理注销成功操作
                        .and().logout().logoutSuccessHandler(myLogoutSuccessHandler);
        //将自定义的JwtTokenAuthenticationFilter插入到过滤器链中的指定的过滤器前面
        http.addFilterAfter(jwtTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        //关闭csrf跨域攻击防御
        http.csrf().disable();
    }
}
