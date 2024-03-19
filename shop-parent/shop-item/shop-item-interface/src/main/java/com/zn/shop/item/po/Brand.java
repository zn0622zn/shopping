package com.zn.shop.item.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zn.core.po.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("brand_")
public class Brand extends BaseEntity {
    private static final long serialVersionUID = 7744713715320481048L;
    /**
     * 品牌名
     */
    @TableField("name_")
    private String name;

    /**
     * 品牌图片对应地址
     */
    @TableField("image_")
    private String image;

    /**
     * 品牌首字母
     */
    @TableField("letter_")
    private String letter;

    /**
     * 瞬时属性，品牌所属的分类，在数据库中无数据
     */
    @TableField(exist = false)
    private Long[] categoryIds;

}
