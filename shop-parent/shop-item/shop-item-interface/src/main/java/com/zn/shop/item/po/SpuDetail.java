package com.zn.shop.item.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zn.core.po.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 张男
 * @date: 2023/12/12---19:14
 */
@Data
@TableName("spu_detail_")
@AllArgsConstructor
@NoArgsConstructor
public class SpuDetail extends BaseEntity {
    private static final long serialVersionUID = 3502427587808673358L;

    /**
     * 为该表定义主键并且主键和spu是同一个，一对一的映射
     */
    @TableId(value = "id_", type = IdType.INPUT)
    protected Long id;
    /**
     * 商品描述
     */
    @TableField("description_")
    private String description;
    /**
     * 商品特殊规格的名称及可选模板
     */
    @TableField("special_spec_")
    private String specialSpec;
    /**
     * 商品的全局规格属性
     */
    @TableField("generic_spec_")
    private String genericSpec;
    /**
     * 包装清单
     */
    @TableField("packing_list_")
    private String packingList;
    /**
     * 售后服务
     */
    @TableField("after_service_")
    private String afterService;
}
