package com.reggie.dto;

import com.reggie.entity.Setmeal;
import com.reggie.entity.SetmealDish;
import lombok.Data;

import java.util.List;

/**
 * 数据转换对象，用来展示套餐（套餐表里面没有套餐名称）和修改套餐（一个套餐需要展示包含的全部菜品）
 */
@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;//存放套餐菜品

    private String categoryName;//存放该套餐所属的套餐分类名称

    private List<SetmealDishDto> setmealDishDtos;//setmealDishDtos 存放含有图片的套餐菜品
}
