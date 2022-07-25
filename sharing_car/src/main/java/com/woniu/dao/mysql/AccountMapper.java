package com.woniu.dao.mysql;

import com.woniu.dao.po.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wdd
 * @since 2022-07-25
 */
@Mapper
public interface AccountMapper extends BaseMapper<Account> {

    @Select("select p.* from perm p inner join role_perm rp on " +
            "p.id=rp.perm_id inner join account a on " +
            "rp.role_id = a.role_id where a.username=#{username}")
    List<String> getPerCodesByPerm(String username);
}
