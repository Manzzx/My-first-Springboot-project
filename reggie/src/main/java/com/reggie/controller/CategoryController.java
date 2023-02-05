package com.reggie.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.reggie.common.R;
import com.reggie.entity.Category;
import com.reggie.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    public R<IPage<Category>> page(Integer page, Integer pageSize) {
        R<IPage<Category>> r = categoryService.page(page, pageSize);
        return r;

    }

    @PostMapping
    public R<String> save(@RequestBody Category category) {
        R<String> r = categoryService.addCategory(category);
        return r;
    }

    @DeleteMapping
    public R<String> delete(Long ids) {
        R<String> r = categoryService.deleteById(ids);
        return r;
    }

    @PutMapping
    public R<String> update(@RequestBody Category category) {
        R<String> r = categoryService.updateById(category);
        return r;
    }

    @GetMapping("/list")
    public R<List<Category>> list(Integer type) {
        R<List<Category>> types = categoryService.getAllByType(type);
        return types;
    }
}
