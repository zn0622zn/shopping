package com.zn.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zn.core.po.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 订单主表
 */
@Data
@TableName("order_")
public class Order extends BaseEntity {

    private static final long serialVersionUID = 3256973269715378204L;
    /**
     * 采用雪花算法，获得主键
     */
    @TableId(value = "id_", type = IdType.INPUT)
    private Long id;

    @TableField("total_num_")
    private Long totalNum;//数量合计

    @TableField("total_money_")
    private Long totalMoney;//金额合计

    @TableField("create_time_")
    private Date createTime;//订单创建时间

    @TableField("username_")
    private String username;//用户名称

    @TableField("buyer_message_")
    private String buyerMessage;//买家留言

    @TableField("order_status_")
    private String orderStatus;//订单状态,0:未完成,1:已完成，2：已退货

    @TableField("pay_status_")
    private String payStatus;//支付状态,0:未支付，1：已支付，2：支付失败

    @TableField("consign_status_")
    private String consignStatus;//发货状态,0:未发货，1：已发货，2：已收货

}