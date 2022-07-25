package com.woniu.dao.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author wdd
 * @since 2022-07-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("parking_order")
public class ParkingOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String orderNumber;

    private Integer robId;

    private Integer parkingId;

    private String parkingAddress;

    private Date startTime;

    private Date endTime;

    private Integer evaid;

    private Integer orderStatus;

    private Double orderMony;

    private Date time;

    private Integer payMethod;


}
