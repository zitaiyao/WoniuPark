package com.woniu.dao.mysql;

import com.woniu.dao.po.Car;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wdd
 * @since 2022-07-25
 */
@Mapper
public interface CarMapper extends BaseMapper<Car> {

}
