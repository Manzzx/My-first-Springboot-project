package com.reggie.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.reggie.common.R;
import com.reggie.dto.DishDto;
import com.reggie.entity.Dish;
import com.reggie.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;


    @GetMapping("/page")
    public R<IPage<DishDto>> page(Long page, Long pageSize, String name) {
        R<IPage<DishDto>> r = dishService.getAllByPage(page, pageSize, name);
        return r;
    }

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        R<String> r = dishService.addDish(dishDto);
        return r;
    }

    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id) {
        R<DishDto> dishDtoR = dishService.getDishDtoById(id);
        return dishDtoR;
    }

    @PutMapping
    public R<String> edit(@RequestBody DishDto dishDto) {
        R<String> r = dishService.updateDishAndFlavor(dishDto);
        return r;
    }

    @DeleteMapping
    public R<String> delete(@RequestParam("ids") List<Long> ids) {
        R<String> r = dishService.deleteById(ids);
        return r;
    }

    @PostMapping("/status/{status}")
    public R<String> updateStatus(@PathVariable Integer status, @RequestParam("ids") List<Long> ids) {
        R<String> r = dishService.updateStatus(status, ids);
        return r;
    }

    /**
     * 通过菜品类型查询或者菜品名称
     *
     * @param categoryId
     * @param name
     * @return
     */
    @GetMapping("/list")
    public R<List<DishDto>> setmealGetAllDishByCategoryIdOrDishName(Long categoryId, String name) {
        if ((categoryId != null) && (categoryId != 0)) {
            return dishService.getDishDtoByCategoryId(categoryId);
        } else {
            return dishService.getDishDtoByDishName(name);
        }
    }
}
