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
@TableName("parking")
public class Parking implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String address;

    private Integer parkingNumber;

    private String propertyRight;

    private Integer phone;

    private Date rentalTime;

    private Double rentaLprice;

    private Integer lessorid;

    private Integer statusA;

    private Integer statusB;

    private Integer statusC;


}
