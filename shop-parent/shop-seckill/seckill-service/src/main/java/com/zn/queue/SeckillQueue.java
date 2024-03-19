package com.zn.queue;

import lombok.Data;

import java.util.Date;

/**
 * 秒杀排对队列
 *
 * @author 张男
 * @date: 2023/12/25---19:27
 */
@Data
public class SeckillQueue {
    //秒杀用户id
    private String uId;
    //创建时间
    private Date createTime;
    //秒杀状态 1:排队中，2:秒杀等待支付,3:支付超时，4:秒杀失败,5:支付完成
    private Integer status;
    //秒杀的商品ID
    private Long goodsId;
    //应付金额
    private Float money;
    //订单号
    private Long orderId;
    //时间段
    private String time;

    public SeckillQueue() {
    }

    public SeckillQueue(String uId, Date createTime, Integer status, Long goodsId, String time) {
        this.uId = uId;
        this.createTime = createTime;
        this.status = status;
        this.goodsId = goodsId;
        this.time = time;
    }

}
