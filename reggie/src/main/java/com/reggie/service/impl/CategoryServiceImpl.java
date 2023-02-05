package com.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.common.BaseContext;
import com.reggie.common.R;
import com.reggie.entity.Category;
import com.reggie.entity.Dish;
import com.reggie.entity.Setmeal;
import com.reggie.exception.BusinessException;
import com.reggie.exception.SystemException;
import com.reggie.mapper.CategoryMapper;
import com.reggie.service.CategoryService;
import com.reggie.service.DishService;
import com.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private DishService dishService;

    /**
     * 分页查询
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public R<IPage<Category>> page(Integer currentPage, Integer pageSize) {
        IPage<Category> iPage = new Page<>(currentPage, pageSize);
        try {
            iPage = categoryMapper.selectPage(iPage, null);
        } catch (RuntimeException e) {
            throw new SystemException("查询到无菜品分类数据");
        }
        return R.success(iPage);
    }

    /**
     * 新增菜品或套餐分类
     *
     * @param category
     * @return
     */
    @Override
    public R<String> addCategory(Category category) {
        //判断一下有没有之前删除过的
        Category oldCategory = categoryMapper.selectByIsDeletedAndName(category);
        ;
        int row = 0;
        if (oldCategory == null) {//如果不是之前删除（标记为1）过的就插入
            try {
                row = categoryMapper.insert(category);
            } catch (RuntimeException e) {
                if (e.getMessage().contains("Duplicate entry")) {
                    throw new BusinessException("分类: " + category.getName() + " 已存在", e);
                } else {
                    throw new SystemException("新增分类失败，请重试", e);
                }
            }
        } else {//之前删除过
            oldCategory.setIsDeleted(0);//标记为0
            oldCategory.setSort(category.getSort());//更新排序
            oldCategory.setUpdateTime(LocalDateTime.now());
            oldCategory.setUpdateUser(BaseContext.getCurrentId());
            try {
                row = categoryMapper.updateIsDeleted(oldCategory);
            } catch (Exception e) {
                throw new SystemException("新增分类失败，请重试", e);
            }
        }
        if (row == 0) {
            throw new SystemException("新增分类出现未知错误");
        }

        return R.success("成功");
    }

    /**
     * 删除分类
     * 需要判断是否有菜品或套餐关联该分类
     *
     * @param id
     * @return
     */
    @Override
    public R<String> deleteById(Long id) {
        //判断是否有菜品或套餐关联该分类
        R<List<Dish>> dishR = dishService.getByCategoryId(id);
        R<List<Setmeal>> setmealR = setmealService.getByCategoryId(id);

        //查询到菜品表有关联
        if (dishR.getCode() == 1 && !dishR.getData().isEmpty()) {
            //不能删除
            throw new BusinessException("查询到菜品表有关联，删除失败");
        }
        //菜品表查询失败
        else if (dishR.getCode() == 0) {
            throw new SystemException("分类删除功能出现异常");
        }
        //查询到套餐表有关联
        else if (setmealR.getCode() == 1 && !setmealR.getData().isEmpty()) {
            //不能删除
            throw new BusinessException("查询到套餐表有关联，删除失败");
        }
        //套餐表查询失败
        else if (setmealR.getCode() == 0) {
            throw new SystemException("分类删除功能出现异常");
        } else {
        }

        //判断完没有任何关联，可以删除
        int i = 0;
        try {
            i = categoryMapper.deleteById(id);
        } catch (RuntimeException e) {
            throw new SystemException("无法删除此分类", e);
        }
        if (i == 0) {
            throw new SystemException("分类删除功能出现未知错误");
        }
        return R.success("成功");
    }

    /**
     * 修改
     *
     * @param category
     * @return
     */
    @Override
    public R<String> updateById(Category category) {
        int row = 0;
        try {
            row = categoryMapper.updateById(category);
        } catch (RuntimeException e) {
            throw new SystemException("修改分类失败", e);
        }
        if (row == 0) {
            throw new SystemException("修改分类出现未知错误");
        }
        return R.success("成功");
    }

    /**
     * 根据分类查询菜品
     *
     * @param type
     * @return
     */
    @Override
    public R<List<Category>> getAllByType(Integer type) {
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        lqw.eq(type != null, Category::getType, type);
        //设置菜品类型展示顺序排序相同就按照更新时间
        lqw.orderByAsc(Category::getType)
                .orderByAsc(Category::getSort)
                .orderByDesc(Category::getUpdateTime);
        List<Category> list = null;
        try {
            list = categoryMapper.selectList(lqw);
        } catch (RuntimeException e) {
            throw new SystemException("菜品类型查询失败", e);
        }
        if (list == null) {
            throw new BusinessException("无");
        }
        return R.success(list);
    }
}
