package com.reggie.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.reggie.common.R;
import com.reggie.dto.SetmealDishDto;
import com.reggie.dto.SetmealDto;
import com.reggie.entity.Setmeal;

import java.util.List;

public interface SetmealService {

    R<List<Setmeal>> getByCategoryId(Long id);

    R<String> deleteById(List<Long> ids);

    R<String> addSetmealAndSetmealDish(SetmealDto setmealDto);

    R<IPage<SetmealDto>> getSetmealDtoByPage(Long currentPage,Long pageSize,String name);

    R<SetmealDto> getDtoById(Long id);

    R<String> updateSetmealAndSetmealDish(SetmealDto setmealDto);

    R<String> updateStatus(Integer status,List<Long> ids);

    R<List<Setmeal>> list(Setmeal setmeal);

    R<List<SetmealDishDto>> getAllDishDto(Long id);

}
