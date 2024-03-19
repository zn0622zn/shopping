package com.zn.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zn.core.po.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @author 张男
 * @date: 2023/12/25---12:14
 */
@Data
@TableName("seckill_goods_")
public class SeckillGoods extends BaseEntity {
    private static final long serialVersionUID = 3465068042969915343L;
    @TableField("spu_id_")
    private Long supId;//spu ID
    @TableField("sku_id_")
    private Long skuId;//sku ID
    @TableField("name_")
    private String name;//标题
    @TableField("small_pic_")
    private String smallPic;//商品图片
    @TableField("price_")
    private String price;//原价格
    @TableField("cost_price_")
    private String costPrice;//秒杀价格
    @TableField("create_time_")
    private Date createTime;//添加日期
    @TableField("check_time_")
    private Date checkTime;//审核日期
    @TableField("status_")
    private String status;//审核状态，0未审核，1审核通过，2审核不通过
    @TableField("start_time_")
    private Date startTime;//开始时间
    @TableField("end_time_")
    private Date endTime;//结束时间
    @TableField("num_")
    private Integer num;//秒杀商品数
    @TableField("stock_count_")
    private Integer stockCount;//剩余库存数
    @TableField("introduction_")
    private String introduction;//描述
    @TableField("date_menu_")
    private String dateMenu;//秒杀时间段
}
