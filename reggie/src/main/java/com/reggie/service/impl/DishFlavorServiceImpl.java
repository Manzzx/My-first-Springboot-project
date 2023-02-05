package com.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.reggie.common.R;
import com.reggie.dto.DishDto;
import com.reggie.entity.DishFlavor;
import com.reggie.exception.SystemException;
import com.reggie.mapper.DishFlavorMapper;
import com.reggie.service.DishFlavorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class DishFlavorServiceImpl implements DishFlavorService {

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    /**
     * 根据菜品id插入菜品口味
     *
     * @param dishDto
     * @return
     */
    @Override
    public R<String> addDishFlavor(DishDto dishDto) {
        int row = 0;
        List<DishFlavor> flavors = dishDto.getFlavors();
        //通过map将自己转换为自己的过程中给dishId赋值
        flavors = flavors.stream().map(dishFlavor -> {
            dishFlavor.setDishId(dishDto.getId());
            return dishFlavor;
        }).collect(Collectors.toList());
        //开始插入
        for (DishFlavor flavor : flavors) {
            try {
                if (!flavor.getName().isEmpty()){
                    row = dishFlavorMapper.insert(flavor);
                }
            } catch (RuntimeException e) {
                throw new SystemException("菜品口味添加失败", e);
            }
        }
        return R.success("成功");
    }

    /**
     * 查询菜品的口味
     * @param id
     * @return
     */
    @Override
    public R<List<DishFlavor>> getAllFlavorByDishId(Long id) {
        LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
        lqw.eq(DishFlavor::getDishId, id);
        List<DishFlavor> dishFlavors = null;
        try {
            dishFlavors = dishFlavorMapper.selectList(lqw);
        } catch (Exception e) {
            throw new SystemException("菜品口味查询失败", e);
        }
        if (dishFlavorMapper == null) {
            return R.error("该菜品没有提供口味");
        }
        return R.success(dishFlavors);
    }
}
