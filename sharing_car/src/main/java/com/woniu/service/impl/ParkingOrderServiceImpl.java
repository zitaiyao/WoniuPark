package com.woniu.service.impl;

import com.woniu.dao.po.ParkingOrder;
import com.woniu.dao.mysql.ParkingOrderMapper;
import com.woniu.service.ParkingOrderService;
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
public class ParkingOrderServiceImpl extends ServiceImpl<ParkingOrderMapper, ParkingOrder> implements ParkingOrderService {

}
