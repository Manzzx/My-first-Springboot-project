package com.reggie.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.reggie.common.R;
import com.reggie.dto.SetmealDishDto;
import com.reggie.dto.SetmealDto;
import com.reggie.entity.Setmeal;
import com.reggie.entity.SetmealDish;
import com.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @PostMapping
    private R<String> save(@RequestBody SetmealDto setmealDto) {
        R<String> r = setmealService.addSetmealAndSetmealDish(setmealDto);
        return r;
    }

    @GetMapping("/page")
    public R<IPage<SetmealDto>> page(Long page, Long pageSize, String name) {
        R<IPage<SetmealDto>> dtos = setmealService.getSetmealDtoByPage(page, pageSize, name);
        return dtos;
    }

    @GetMapping("/{id}")
    public R<SetmealDto> getDtoByIdForUpdate(@PathVariable Long id){
        R<SetmealDto> dtoById = setmealService.getDtoById(id);
        return dtoById;
    }

    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto){
        R<String> r = setmealService.updateSetmealAndSetmealDish(setmealDto);
        return r;
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        R<String> r = setmealService.deleteById(ids);
        return r;
    }

    @PostMapping("/status/{status}")
    public R<String> status(@PathVariable Integer status,@RequestParam List<Long> ids){
        R<String> r = setmealService.updateStatus(status, ids);
        return r;
    }

    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        R<List<Setmeal>> list = setmealService.list(setmeal);
        return list;
    }

    @GetMapping("/dish/{id}")
    public R<List<SetmealDishDto>> getDish(@PathVariable Long id){

        return setmealService.getAllDishDto(id);
    }

}
