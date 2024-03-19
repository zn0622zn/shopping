package com.zn.shop.item.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zn.core.po.BaseTreeEntity;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 商品分类
 */
@Data
@TableName("category_")
public class Category extends BaseTreeEntity {

    private static final long serialVersionUID = 371375534253222857L;
    /**
     * 是否为父节点
     */
    @TableField("is_parent_")
    private Boolean isParent = false;

    /**
     * 查询根节点条件
     */
    @TableField(exist = false)
    private Integer isRoot = 0;

    /**
     * 前端 组件需要(treeSelect)
     */
    public String getLabel() {
        return this.getTitle();
    }

}
