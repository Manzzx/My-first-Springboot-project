package com.reggie.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.reggie.common.R;
import com.reggie.dto.DishDto;
import com.reggie.entity.Dish;

import java.util.List;

public interface DishService {

    R<List<Dish>> getByCategoryId(Long id);

    R<String> deleteById(List<Long> ids);

    R<IPage<DishDto>> getAllByPage(Long currentPage, Long pageSize, String name);

    R<String> addDish(DishDto dish);

    R<DishDto> getDishDtoById(Long id);

    R<String> updateDishAndFlavor(DishDto dishDto);

    R<String> updateStatus(Integer status,List<Long> ids);

    R<List<DishDto>> getDishDtoByCategoryId(Long categoryId);

    R<List<DishDto>> getDishDtoByDishName(String name);


}
