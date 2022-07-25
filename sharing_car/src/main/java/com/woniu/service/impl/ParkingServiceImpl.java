package com.woniu.service.impl;

import com.woniu.dao.po.Parking;
import com.woniu.dao.mysql.ParkingMapper;
import com.woniu.service.ParkingService;
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
public class ParkingServiceImpl extends ServiceImpl<ParkingMapper, Parking> implements ParkingService {

}
