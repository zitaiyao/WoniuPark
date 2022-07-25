package com.woniu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniu.dao.po.Account;
import com.woniu.dao.mysql.AccountMapper;
import com.woniu.service.AccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wdd
 * @since 2022-07-25
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account>
        implements AccountService, UserDetailsService {

    @Autowired
    private AccountMapper accountMapper;

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DisabledException, LockedException {
        QueryWrapper<Account> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        Account user = accountMapper.selectOne(wrapper);

        if(user==null){
            throw new UsernameNotFoundException("用户名不存在!");
        }

        //封装用户的权限集
        List<GrantedAuthority> authorities = new ArrayList<>();

        //查询用户的权限集
        List<String> perCodes = accountMapper.getPerCodesByPerm(user.getUsername());

        perCodes.forEach(perCode->{
            authorities.add(new SimpleGrantedAuthority(perCode));
        });

        UserDetails userDetails = null;

        userDetails = new org.springframework.security.core.userdetails.User(
                user.getUsername(),user.getPassword(),authorities);
        return userDetails;
    }
}
