package com.zn.shop.item.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zn.core.po.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 张男
 * @date: 2023/12/11---21:23
 */
@Data
@TableName("spec_group_")
@AllArgsConstructor
@NoArgsConstructor
public class SpecGroup extends BaseEntity {
    private static final long serialVersionUID = 8163951827082522908L;

    /**
     *规格组所属的分类id
     */
    @TableField("cid_")
    private Long cid;

    /**
     * 规格组的名称
     */
    @TableField("name_")
    private String name;

    /**
     * 当前规格组的所有规格项
     */
    @TableField(exist = false)
    private List<SpecParam> params;
}
