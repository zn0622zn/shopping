package com.zn.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zn.core.po.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 张男
 * @date: 2024/1/23---19:53
 */
@Data
@TableName("user_")
public class User extends BaseEntity {
    private static final long serialVersionUID = -2900191772453374628L;
    /**
     * 用户名
     */
    @TableField("user_name_")
    private String userName;
    /**
     * 密码(加密)
     */
    @TableField("password_")
    private String password;
    /**
     * 真实姓名
     */
    @TableField("real_name_")
    private String realName;
    /**
     * 性别
     */
    @TableField("sex_")
    private String sex;
    /**
     * 是否锁定状态
     */
    @TableField("lock_")
    private Boolean lock;
    /**
     * 所属部门
     */
    @TableField("dept_id_")
    private Long deptId;
    /**
     * 工作岗位
     */
    @TableField("post_id_")
    private Long postId;
    /**
     * 部门名称
     */
    @TableField(exist = false)
    private String deptName;
    /**
     * 该用户的权限id
     */
    @TableField(exist = false)
    private Long[] roleIds;
    /**
     * 岗位名称
     */
    @TableField(exist = false)
    private String postName;
    /**
     * 用户积分
     */
    @TableField("point_")
    private Long point_;
}
