package com.zn.core.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(value = {"handler"})
public class BaseTreeEntity extends BaseEntity {

    private static final long serialVersionUID = 652316956431206501L;
    /**
     * 排序字段
     */
    @TableField("order_")
    private Integer order;

    /**
     * 父节点id
     */
    @TableField("parent_id_")
    private Long parentId;

    /**
     * 节点名称
     */
    @TableField("title_")
    private String title;

    /**
     * 是否展开节点，默认不展开
     */
    @TableField("expand_")
    private Boolean expand = false;



}
