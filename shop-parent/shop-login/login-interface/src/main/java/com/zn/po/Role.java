package com.zn.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zn.core.po.BaseEntity;
import lombok.Data;

/**
 * @author 张男
 * @date: 2024/1/24---19:01
 */
@Data
@TableName("role_")
public class Role extends BaseEntity {
    private static final long serialVersionUID = -9183867137751866480L;
    /**
     * 权限
     */
    @TableField("name_")
    private String name;
    /**
     * 权限名
     */
    @TableField("title_")
    private String title;
    /**
     * 描述
     */
    @TableField("desc_")
    private String desc;
    /**
     * 有该权限的userId
     */
    @TableField(exist = false)
    private Long[] userIds;
}
