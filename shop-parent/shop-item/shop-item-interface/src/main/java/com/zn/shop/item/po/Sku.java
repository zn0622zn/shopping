package com.zn.shop.item.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zn.core.po.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 张男
 * @date: 2023/12/12---19:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sku_")
public class Sku extends BaseEntity {
    private static final long serialVersionUID = 216346984827745274L;
    /**
     * 外键，spu的id
     */
    @TableField("spu_id_")
    private Long spuId;
    /**
     * 标题
     */
    @TableField("title_")
    private String title;
    /**
     * 商品对应图片地址
     */
    @TableField("images_")
    private String images;
    /**
     * 商品价格
     */
    @TableField("price_")
    private Long price;
    /**
     * 商品特殊规格的键值对
     */
    @TableField("own_spec_")
    private String ownSpec;
    /**
     * 商品特殊规格的下标
     */
    @TableField("indexes_")
    private String indexes;
    /**
     * 是否有效，逻辑删除用
     */
    @TableField("enable_")
    private Boolean enable;
    /**
     * 创建时间
     */
    @TableField("create_time_")
    private Date createTime;
    /**
     * 最后修改时间
     */
    @TableField("last_update_time_")
    private Date lastUpdateTime;
    /**
     * 库存
     */
    @TableField("stock_")
    private Integer stock;
}
