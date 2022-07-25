package com.woniu.service.impl;

import com.woniu.dao.po.Tenants;
import com.woniu.dao.mysql.TenantsMapper;
import com.woniu.service.TenantsService;
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
public class TenantsServiceImpl extends ServiceImpl<TenantsMapper, Tenants> implements TenantsService {

}
