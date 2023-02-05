package com.reggie.dto;

import com.reggie.entity.SetmealDish;
import lombok.Data;

/**
 * 扩展套餐菜品表图片属性
 */
@Data
public class SetmealDishDto extends SetmealDish {

    private String image;//菜品图片
}
