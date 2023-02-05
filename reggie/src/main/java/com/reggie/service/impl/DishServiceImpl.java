package com.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.common.BaseContext;
import com.reggie.common.R;
import com.reggie.dto.DishDto;
import com.reggie.entity.Dish;
import com.reggie.entity.DishFlavor;
import com.reggie.entity.Setmeal;
import com.reggie.entity.SetmealDish;
import com.reggie.exception.BusinessException;
import com.reggie.exception.SystemException;
import com.reggie.mapper.DishFlavorMapper;
import com.reggie.mapper.DishMapper;
import com.reggie.mapper.SetmealDishMapper;
import com.reggie.mapper.SetmealMapper;
import com.reggie.service.DishFlavorService;
import com.reggie.service.DishService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Value("${reggie.basePath}")
    private String basePath;
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;


    /**
     * 根据分类id查找所有菜品
     *
     * @param id
     * @return
     */
    @Override
    public R<List<Dish>> getByCategoryId(Long id) {
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<Dish>();
        lqw.eq(Dish::getCategoryId, id);
        List<Dish> dishes = null;
        try {
            dishes = dishMapper.selectList(lqw);
        } catch (RuntimeException e) {
            throw new SystemException("菜品查询失败", e);
        }

        return R.success(dishes);
    }

    /**
     * 通过id删除菜品
     *
     * @param ids
     * @return
     */
    @Override
    public R<String> deleteById(List<Long> ids) {

        try {
            ids.stream().forEach(id -> {
                //先删除图片
                LambdaQueryWrapper<Dish> lqwD = new LambdaQueryWrapper<>();
                lqwD.eq(Dish::getId, id);
                Dish dish = dishMapper.selectById(id);
                //先判断该菜品是否已经停售
                Integer status = dish.getStatus();
                //status==0证明已经停售可以删除
                if (status == 0) {
                    //数据库里图片没被标记为null
                    if (!"null".equals(dish.getImage())) {
                        Path path = Paths.get(basePath + dish.getImage());
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        int img = dishMapper.updateDishImgToNull(id);
                    }
                    //删除菜品
                    int i = dishMapper.deleteById(id);
                    //开始删除关联菜品的口味表
                    if (i != 0) {
                        LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
                        lqw.eq(DishFlavor::getDishId, id);
                        int delete = dishFlavorMapper.delete(lqw);
                    } else {
                        throw new SystemException("菜品删除失败");
                    }
                } else {
                    throw new BusinessException("菜品: " + dish.getName() + " 正在售卖中，无法删除");
                }
            });
        } catch (RuntimeException e) {
            throw e;
        }
        return R.success("成功");
    }

    /**
     * 菜品分页查询
     *
     * @param currentPage
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public R<IPage<DishDto>> getAllByPage(Long currentPage, Long pageSize, String name) {
        IPage<DishDto> ipage = new Page<>(currentPage, pageSize);//配合前端用来传输数据

        Long total = 0L;//存储数据总数
        try {
            Long start = (currentPage - 1) * pageSize;
            List<DishDto> dishDtos = null;
            if (Strings.isNotEmpty(name) && name.trim().length() != 0) {//条件分页查询
                dishDtos = dishMapper.selectDishDtoByDishJoinCategoryWithName(name.trim(), start, pageSize);

                LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
                lqw.like(Dish::getName, name.trim());
                total = Long.valueOf(dishMapper.selectCount(lqw));//通过名称查询总数
            } else { //全部分页查询
                dishDtos = dishMapper.selectDishDtoByDishJoinCategory(start, pageSize);

                total = Long.valueOf(dishMapper.selectCount(null));//查询总数
            }
            if (dishDtos == null) {
                throw new BusinessException("店铺还没有任何菜品喔", new RuntimeException("店铺还没有任何菜品喔"));
            } else {
            }
            //给ipage赋值
            ipage.setRecords(dishDtos);
            ipage.setTotal(total);
//            ipage.setPages(0);//没用到就不搞了
        } catch (RuntimeException e) {
            throw new SystemException("菜品查询失败", e);
        }
        return R.success(ipage);
    }

    /**
     * 添加菜品同时添加口味多表查询
     *
     * @param dishDto
     * @return
     */
    @Override
    @Transactional
    public R<String> addDish(DishDto dishDto) {
        Dish oldDish = dishMapper.selectByIsDeletedAndName(dishDto);
        int row = 0;
        if (oldDish == null) {//没有被标记删除过（新菜品）
            try {
                row = dishMapper.insert(dishDto);
                //插入菜品前mybatisPlus会给id赋值再插入数据库
                dishFlavorService.addDishFlavor(dishDto);
            } catch (RuntimeException e) {
                if (e.getMessage().contains("Duplicate entry")) {
                    throw new BusinessException("菜品: " + dishDto.getName() + " 已存在", e);
                }
                throw new SystemException(e.getMessage(), e);

            }
        } else {//有被标记删除过
            //更新菜品信息
            oldDish.setName(dishDto.getName());
            oldDish.setCategoryId(dishDto.getCategoryId());
            oldDish.setPrice(dishDto.getPrice());
            oldDish.setImage(dishDto.getImage());
            oldDish.setDescription(dishDto.getDescription());
            oldDish.setCreateTime(LocalDateTime.now());
            oldDish.setUpdateTime(LocalDateTime.now());
            oldDish.setCreateUser(BaseContext.getCurrentId());
            oldDish.setUpdateUser(BaseContext.getCurrentId());
            oldDish.setIsDeleted(0);
            try {
                row = dishMapper.updateIsDeleted(oldDish);
                dishDto.setId(oldDish.getId());
                //插入菜品前mybatisPlus会给id赋值再插入数据库
                dishFlavorService.addDishFlavor(dishDto);
            } catch (RuntimeException e) {
                throw new SystemException(e.getMessage());
            }
        }
        if (row == 0) {
            throw new SystemException("新增菜品出了点问题...");
        }
        return R.success("成功");
    }

    /**
     * 回显修改操作数据
     *
     * @param id
     * @return
     */
    @Override
    public R<DishDto> getDishDtoById(Long id) {
        DishDto dishDto = null;
        try {
            dishDto = dishMapper.selectDishDtoByDishJoinCategoryWithId(id);
            if (dishDto != null) {
                R<List<DishFlavor>> flavorsR = dishFlavorService.getAllFlavorByDishId(id);//该菜品口味
                if (flavorsR.getCode() != 0) {//该菜品有提供口味
                    dishDto.setFlavors(flavorsR.getData());
                }
            } else {
                throw new SystemException("菜品查询出现未知错误");
            }
        } catch (Exception e) {
            throw new SystemException("菜品查询失败", e);
        }

        return R.success(dishDto);
    }

    /**
     * 修改dish表和dish_flavor表
     *
     * @param dishDto
     * @return
     */
    @Override
    @Transactional
    public R<String> updateDishAndFlavor(DishDto dishDto) {
        //判断是否修改了图片
        Dish dish = dishMapper.selectById(dishDto.getId());
        //查出来数据库图片和传输过来的图片不一致
        if (!dish.getImage().equals(dishDto.getImage())) {
            //删除旧图片
            Path path = Paths.get(basePath + dish.getImage());
            try {
                Files.delete(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        System.out.println(dishDto);
        lqw.eq(Dish::getId, dishDto.getId());
        int row = 0;
        try {
            //更新dish表
            row = dishMapper.update(dishDto, lqw);
            //更新flavor表
            List<DishFlavor> flavors = dishDto.getFlavors();

            //先删除掉原先该菜品所有口味
            LambdaQueryWrapper<DishFlavor> lqwF = new LambdaQueryWrapper<>();
            lqwF.eq(DishFlavor::getDishId, dishDto.getId());
            int delete = dishFlavorMapper.delete(lqwF);

            flavors.stream().forEach(dishFlavor -> {
                //用来判断用户是否存在一次添加多个相同的口味名称，如相同会被最后一个口味覆盖
                LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(DishFlavor::getName, dishFlavor.getName())
                        .eq(DishFlavor::getDishId, dishDto.getId());//根据菜品id和口味名称查询是否已经存在该口味
                //查询是否存在该口味
                List<DishFlavor> isExitFlavor = dishFlavorMapper.selectList(lambdaQueryWrapper);
                //新添加的口味
                if (isExitFlavor.isEmpty() && !dishFlavor.getName().isEmpty()) {
                    dishFlavor.setDishId(dishDto.getId());
                    int i = dishFlavorMapper.insert(dishFlavor);
                } else {//更新口味
                    if (!dishFlavor.getName().isEmpty()) {
                        LambdaQueryWrapper<DishFlavor> lqw1 = new LambdaQueryWrapper<>();
                        lqw1.eq(DishFlavor::getDishId, dishDto.getId())
                                .eq(DishFlavor::getName, dishFlavor.getName());//根据菜品id和口味名称更新
                        int update = dishFlavorMapper.update(dishFlavor, lqw1);
                    }
                }
            });
        } catch (RuntimeException e) {
            throw new SystemException("菜品修改失败", e);
        }
        if (row == 0) {
            throw new SystemException("菜品修改出现未知错误");
        }

        return R.success("成功");
    }

    /**
     * 修改菜品销售状态
     *
     * @param status
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public R<String> updateStatus(Integer status, List<Long> ids) {
        Dish dish = new Dish();
        dish.setStatus(status);
        //如果是菜品停售
        if (status == 0) {
            ids.stream().forEach(id -> {
                LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
                lqw.eq(Dish::getId, id);//更新状态
                try {
                    int update = dishMapper.update(dish, lqw);
                    //查询该菜品是否有被包含在套餐中
                    LambdaQueryWrapper<SetmealDish> lqwSD = new LambdaQueryWrapper<>();
                    lqwSD.eq(SetmealDish::getDishId, id);
                    //套餐菜品表里所有该菜品的集合
                    List<SetmealDish> setmealDishes = setmealDishMapper.selectList(lqwSD);
                    //开始停售含有该菜品的套餐
                    Setmeal setmeal = new Setmeal();
                    setmeal.setStatus(0);
                    setmealDishes.stream().forEach(setmealDish -> {
                        LambdaQueryWrapper<Setmeal> lqwS = new LambdaQueryWrapper<>();
                        lqwS.eq(Setmeal::getId, setmealDish.getSetmealId());
                        //套餐停售
                        setmealMapper.update(setmeal, lqwS);
                    });
                } catch (Exception e) {
                    throw new SystemException("菜品状态修改失败", e);
                }
            });
        } else {
            ids.stream().forEach(id -> {
                LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
                lqw.eq(Dish::getId, id);//更新状态
                try {
                    int update = dishMapper.update(dish, lqw);
                } catch (Exception e) {
                    throw new SystemException("菜品状态修改失败", e);
                }
            });
        }
        return R.success("成功");
    }

    /**
     * 新增套餐回显每一个菜品类型的所有菜品
     *
     * @param categoryId
     * @return
     */
    @Override
    public R<List<DishDto>> getDishDtoByCategoryId(Long categoryId) {
        List<Dish> dishs = null;
        List<DishDto> dishDtos = null;
        try {
            dishs = dishMapper.selectDishByCategoryId(categoryId);
            //查询菜品口味
            dishDtos = dishs.stream().map(dish -> {
                DishDto dishDto = new DishDto();
                R<List<DishFlavor>> flavorR = dishFlavorService.getAllFlavorByDishId(dish.getId());
                //将dish copy到dishDto
                BeanUtils.copyProperties(dish, dishDto, "flavors");
                dishDto.setFlavors(flavorR.getData());
                return dishDto;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            throw e;
        }
        return R.success(dishDtos);
    }

    /**
     * 新增套餐通过名称回显菜品，搜索功能
     *
     * @param name
     * @return
     */
    @Override
    public R<List<DishDto>> getDishDtoByDishName(String name) {
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        lqw.like(name != null, Dish::getName, name != null ? name.trim() : null);
        lqw.eq(Dish::getStatus, 1);
        List<Dish> dishes = dishMapper.selectList(lqw);
        List<DishDto> dishDtos = null;
        dishDtos = dishes.stream().map(new Function<Dish, DishDto>() {
            @Override
            public DishDto apply(Dish dish) {
                DishDto dishDto = new DishDto();
                //将dish copy到dishDto
                BeanUtils.copyProperties(dish, dishDto, "flavors");
                return dishDto;
            }
        }).collect(Collectors.toList());
        return R.success(dishDtos);
    }
}
