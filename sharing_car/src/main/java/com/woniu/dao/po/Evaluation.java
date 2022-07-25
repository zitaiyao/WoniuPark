package com.woniu.dao.po;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("evaluation")
public class Evaluation implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer robId;

    private String robEva;

    private String robDes;

    private Integer lessorId;

    private String lessorEva;

    private String lessorDes;


}
