package com.reggie.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.reggie.common.R;
import com.reggie.entity.Category;

import java.util.List;

public interface CategoryService {

    R<IPage<Category>> page(Integer currentPage, Integer pageSize);

    R<String> addCategory(Category category);

    R<String> deleteById(Long id);

    R<String> updateById(Category category);

    R<List<Category>> getAllByType(Integer type);
}
