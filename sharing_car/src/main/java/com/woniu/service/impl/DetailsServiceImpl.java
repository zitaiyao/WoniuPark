package com.woniu.service.impl;

import com.woniu.dao.po.Details;
import com.woniu.dao.mysql.DetailsMapper;
import com.woniu.service.DetailsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wdd
 * @since 2022-07-25
 */
@Service
public class DetailsServiceImpl extends ServiceImpl<DetailsMapper, Details> implements DetailsService {

}
