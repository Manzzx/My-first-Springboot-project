package com.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 菜品
 */
@Data
public class Dish implements Serializable {

    private static final long serialVersionUID=1L;

    private Long id;

    private String name;//菜品名称

    private Long categoryId;//属于那种菜品分类

    private BigDecimal price;

    private String code;//商品码

    private String image;//菜品图片

    private String description;//描述信息

    private Integer status;//0停售 1 起售

    private Integer sort;//排序顺序

    @TableField(fill = FieldFill.INSERT)//插入时自动填充字段
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)//更新时自动填充字段
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    //是否删除
    private Integer isDeleted;






}
