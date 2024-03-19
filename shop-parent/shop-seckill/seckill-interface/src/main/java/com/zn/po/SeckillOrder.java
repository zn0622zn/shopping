package com.zn.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zn.core.po.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @author 张男
 * @date: 2023/12/25---12:15
 */
@Data
@TableName("seckill_order_")
public class SeckillOrder extends BaseEntity {
    private static final long serialVersionUID = -7922135082097943902L;

    @TableId(value = "id_", type=IdType.AUTO)
    private Long id;
    @TableField("seckill_id_")
    private Long seckillId;//秒杀商品ID
    @TableField("money_")
    private String money;//支付金额
    @TableField("user_id_")
    private String userId;//用户
    @TableField("create_time_")
    private Date createTime;//创建时间
    @TableField("pay_time_")
    private Date payTime;//支付时间
    @TableField("status_")
    private String status;//状态，0未支付，1已支付
    @TableField("transaction_id_")
    private String transactionId;//交易流水
}
