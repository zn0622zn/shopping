package com.zn.shop.item.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zn.core.po.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 张男
 * @date: 2023/12/11---21:54
 */
@Data
@TableName("spec_param_")
@AllArgsConstructor
@NoArgsConstructor
public class SpecParam extends BaseEntity {
    private static final long serialVersionUID = 1550364400968815966L;

    /**
     * 规格项的分类id
     */
    @TableField("cid_")
    private Long cid;

    /**
     *规格项对应的规格组id
     */
    @TableField("group_id_")
    private Long groupId;

    /**
     *规格项名称
     */
    @TableField("name_")
    private String name;

    /**
     *规格项是否为数组
     */
    @TableField("numeric_")
    private Boolean numeric;

    /**
     *规格项单位
     */
    @TableField("unit_")
    private String unit;

    /**
     *是否为通用规格参数，false为sku参数
     */
    @TableField("generic_")
    private Boolean generic;

    /**
     *是否为搜索项 若是可进行范围的选择
     */
    @TableField("searching_")
    private Boolean searching;

    /**
     *排序
     */
    @TableField("segments_")
    private String segments;

}
