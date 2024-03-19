package com.zn.shop.item.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zn.core.po.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author 张男
 * @date: 2023/12/12---19:04
 */
@Data
@TableName("spu_")
@AllArgsConstructor
@NoArgsConstructor
public class Spu extends BaseEntity {
    private static final long serialVersionUID = 5522622815624504244L;
    /**
     * 品牌ID
     */
    @TableField("brand_id_")
    private Long brandId;
    /**
     *一级分类ID
     */
    @TableField("cid1_")
    private Long cid1;
    /**
     *二级分类ID
     */
    @TableField("cid2_")
    private Long cid2;
    /**
     *三级分类ID
     */
    @TableField("cid3_")
    private Long cid3;
    /**
     *标题
     */
    @TableField("title_")
    private String title;
    /**
     *子标题
     */
    @TableField("sub_title_")
    private String subTitle;
    /**
     *是否上架，0下架，1上架
     */
    @TableField("saleable_")
    private Boolean saleable;
    /**
     *是否有效，0已删除，1有效，逻辑删除使用
     */
    @TableField("valid_")
    private Boolean valid;
    /**
     *创建时间
     */
    @TableField("create_time_")
    private Date createTime;
    /**
     * 最后修改时间
     */
    @TableField("last_update_time_")
    private Date lastUpdateTime;
    /**
     * Spu详情对象，存大文本数据，如，描述等
     */
    @TableField(exist = false)
    private SpuDetail spuDetail;
    /**
     * spu对应的sku集合
     */
    @TableField(exist = false)
    private List<Sku> skus;
    /**
     * 品牌名称，查询时显示，代替brand_id_
     */
    @TableField(exist = false)
    private String brandName;
    /**
     * 分类名称，查询时显示，代替cid
     */
    @TableField(exist = false)
    private String categoryName;
}
