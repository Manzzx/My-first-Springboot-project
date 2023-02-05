package com.reggie.service;

import com.reggie.common.R;
import com.reggie.dto.DishDto;
import com.reggie.entity.DishFlavor;

import java.util.List;

public interface DishFlavorService {

    R<String> addDishFlavor(DishDto dishDto);

    R<List<DishFlavor>> getAllFlavorByDishId(Long id);
}
