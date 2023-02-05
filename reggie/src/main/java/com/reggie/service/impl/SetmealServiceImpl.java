package com.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.common.BaseContext;
import com.reggie.common.R;
import com.reggie.dto.DishDto;
import com.reggie.dto.SetmealDishDto;
import com.reggie.dto.SetmealDto;
import com.reggie.entity.Category;
import com.reggie.entity.Dish;
import com.reggie.entity.Setmeal;
import com.reggie.entity.SetmealDish;
import com.reggie.exception.BusinessException;
import com.reggie.exception.SystemException;
import com.reggie.mapper.CategoryMapper;
import com.reggie.mapper.DishMapper;
import com.reggie.mapper.SetmealDishMapper;
import com.reggie.mapper.SetmealMapper;
import com.reggie.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Value("${reggie.basePath}")
    private String basePath;
    @Autowired
    private DishMapper dishMapper;


    /**
     * 根据分类id查找所有套餐
     *
     * @param id
     * @return
     */
    @Override
    public R<List<Setmeal>> getByCategoryId(Long id) {
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<Setmeal>();
        lqw.eq(Setmeal::getCategoryId, id);
        List<Setmeal> setmeals = null;
        try {
            setmeals = setmealMapper.selectList(lqw);
        } catch (Exception e) {
            throw new SystemException("套餐查失败", e);
        }
        return R.success(setmeals);
    }

    /**
     * 根据id删除套餐
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public R<String> deleteById(List<Long> ids) {
        try {
            ids.stream().forEach(id -> {
                //查询出该套餐
                Setmeal setmeal = setmealMapper.selectById(id);
                //先判断该套餐是否已经停售
                Integer status = setmeal.getStatus();
                //status==0证明已经停售可以删除
                if (status == 0) {
                    //开始删除原图片
                    Path path = Paths.get(basePath + setmeal.getImage());
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //删除套餐
                    int setmealDelete = setmealMapper.deleteById(id);
                    if (setmealDelete != 0) {//套餐删除成功
                        //开始删除套餐菜品
                        LambdaQueryWrapper<SetmealDish> lqw = new LambdaQueryWrapper<>();
                        lqw.eq(SetmealDish::getSetmealId, id);
                        setmealDishMapper.delete(lqw);
                    } else {
                        throw new SystemException("套餐删除失败");
                    }
                } else {
                    throw new BusinessException("套餐: " + setmeal.getName() + " 正在售卖中，无法删除");
                }
            });
        } catch (RuntimeException e) {
            throw new SystemException(e.getMessage(), e);
        }
        return R.success("成功");
    }

    /**
     * 新增套餐
     *
     * @param setmealDto
     * @return
     */
    @Override
    @Transactional
    public R<String> addSetmealAndSetmealDish(SetmealDto setmealDto) {
        //查询新增套餐是否被标记删除过
        Setmeal setmeal = setmealMapper.selectSetmealIsDeletedByName(setmealDto.getName());
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();//获取套餐里面的菜品
        //没有被标记过
        if (setmeal == null) {
            //插入套餐表
            try {
                setmealMapper.insert(setmealDto);
            } catch (Exception e) {
                if (e.getMessage().contains("Duplicate entry")) {
                    throw new BusinessException("套餐: " + setmealDto.getName() + " 已存在", e);
                }
                throw e;
            }
            //遍历插入套餐菜品表
            setmealDishes.stream().map(setmealDish -> {
                setmealDish.setSetmealId(setmealDto.getId());
                try {
                    setmealDishMapper.insert(setmealDish);
                } catch (Exception e) {
                    throw new SystemException("套餐菜品插入失败", e);
                }
                return setmealDish;
            }).collect(Collectors.toList());
        } else {//被标记过开始进行更新操作
            //开始更新属性
            setmeal.setName(setmealDto.getName());
            setmeal.setCategoryId(setmealDto.getCategoryId());
            setmeal.setPrice(setmealDto.getPrice());
            setmeal.setStatus(1);
            setmeal.setDescription(setmealDto.getDescription());
            setmeal.setImage(setmealDto.getImage());
            setmeal.setCreateTime(LocalDateTime.now());
            setmeal.setUpdateTime(LocalDateTime.now());
            setmeal.setCreateUser(BaseContext.getCurrentId());
            setmeal.setUpdateUser(BaseContext.getCurrentId());
            int i = setmealMapper.updateIsDeletedById(setmeal);
            //遍历插入套餐菜品表
            setmealDishes.stream().map(setmealDish -> {
                setmealDish.setSetmealId(setmeal.getId());
                try {
                    setmealDishMapper.insert(setmealDish);
                } catch (Exception e) {
                    throw new SystemException("套餐菜品插入失败", e);
                }
                return setmealDish;
            }).collect(Collectors.toList());
            if (i == 0) {
                throw new SystemException("套餐菜品插入失败");
            }
        }
        return R.success("成功");
    }

    /**
     * 分页查询套餐
     *
     * @param currentPage
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public R<IPage<SetmealDto>> getSetmealDtoByPage(Long currentPage, Long pageSize, String name) {
        IPage<Setmeal> iPage = new Page<>(currentPage, pageSize);
        IPage<SetmealDto> dtoIPage = new Page<>();
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.like(name != null, Setmeal::getName, name != null ? name.trim() : null);
        //先通过分页查询setmeal表
        iPage = setmealMapper.selectPage(iPage, lqw);

        List<Setmeal> records = iPage.getRecords();
        List<SetmealDto> setmealDtos = records.stream().map(setmeal -> {
            SetmealDto setmealDto = new SetmealDto();
            Category category = categoryMapper.selectById(setmeal.getCategoryId());//查询该套餐的类型获得类型名称
            setmealDto.setCategoryName(category.getName());//赋值类型名称
            BeanUtils.copyProperties(setmeal, setmealDto);//将套餐的所有信息拷贝到dto对象中
            return setmealDto;
        }).collect(Collectors.toList());
        //开始填充最终传输对象（dtoIPage）属性
        BeanUtils.copyProperties(iPage, dtoIPage, "records");//因为集合类型不同不能直接拷贝所以先排除
        dtoIPage.setRecords(setmealDtos);
        return R.success(dtoIPage);
    }

    /**
     * 回显套餐修改页面
     *
     * @param id
     * @return
     */
    @Override
    public R<SetmealDto> getDtoById(Long id) {
        //dto缺啥查啥，除了categoryName其他都要
        SetmealDto setmealDto = new SetmealDto();
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Setmeal::getId, id);
        Setmeal setmeal = null;
        List<SetmealDish> setmealDishes = null;
        try {
            setmeal = setmealMapper.selectById(id);
            //查询套餐菜品表
            LambdaQueryWrapper<SetmealDish> lqwDish = new LambdaQueryWrapper<>();
            lqwDish.eq(SetmealDish::getSetmealId, id);
            setmealDishes = setmealDishMapper.selectList(lqwDish);
        } catch (Exception e) {
            throw new SystemException(e.getMessage(), e);
        }
        //开始拷贝
        BeanUtils.copyProperties(setmeal, setmealDto);
        setmealDto.setSetmealDishes(setmealDishes);
        return R.success(setmealDto);
    }

    /**
     * 更新套餐
     *
     * @param setmealDto
     * @return
     */
    @Override
    @Transactional
    public R<String> updateSetmealAndSetmealDish(SetmealDto setmealDto) {
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<SetmealDish> lqwDish = new LambdaQueryWrapper<>();
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDto, setmeal);//将setmeal表的字段拷贝出来
        lqw.eq(Setmeal::getId, setmealDto.getId());

        //判断原图片和更新图片是否一样
        Setmeal selectById = setmealMapper.selectById(setmealDto.getId());
        if (!selectById.getImage().equals(setmealDto.getImage())) {//不一样
            //开始删除原图片
            Path path = Paths.get(basePath + selectById.getImage());
            try {
                Files.delete(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //根据id更新setmeal
        int update = setmealMapper.update(setmeal, lqw);
        lqwDish.eq(SetmealDish::getSetmealId, setmealDto.getId());
        if (update != 0) {
            //先删除套餐的原菜品
            int delete = setmealDishMapper.delete(lqwDish);
            List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
            //开始插入套餐菜品
            setmealDishes.stream().forEach(setmealDish -> {
                setmealDish.setSetmealId(setmealDto.getId());
                try {
                    int insert = setmealDishMapper.insert(setmealDish);
                } catch (Exception e) {
                    throw e;
                }
            });
        }
        return R.success("成功");
    }

    /**
     * 更新状态
     *
     * @param status
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public R<String> updateStatus(Integer status, List<Long> ids) {
        Setmeal setmeal = new Setmeal();
        setmeal.setStatus(status);
        //套餐起售前判断该套餐中包含的菜品是否有停售的
        if (status == 1) {
            ids.stream().forEach(id -> {
                //开始查询所有套餐菜品
                LambdaQueryWrapper<SetmealDish> lqwSD = new LambdaQueryWrapper<>();
                lqwSD.eq(SetmealDish::getSetmealId, id);
                //所有菜品
                List<SetmealDish> setmealDishes = setmealDishMapper.selectList(lqwSD);
                //通过套餐菜品表的dishId查询dish表的菜品状态
                setmealDishes.stream().forEach(setmealDish -> {
                    Dish dish = dishMapper.selectById(setmealDish.getDishId());
                    Integer dishStatus = dish.getStatus();
                    //查询到有菜品处于停售状态
                    if (dishStatus == 0) {
                        throw new BusinessException("套餐有菜品处于停售中无法起售");
                    }
                });
                LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
                lqw.eq(Setmeal::getId, id);
                try {
                    int i = setmealMapper.update(setmeal, lqw);
                } catch (Exception e) {
                    if (e.getMessage().contains("菜品处于停售中")) {
                        throw e;
                    }
                    throw new SystemException("套餐状态更新失败", e);
                }
            });
        } else {
            ids.stream().forEach(id -> {
                LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
                lqw.eq(Setmeal::getId, id);
                try {
                    int i = setmealMapper.update(setmeal, lqw);
                } catch (Exception e) {
                    throw new SystemException("套餐状态更新失败", e);
                }
            });
        }
        return R.success("成功");
    }

    /**
     * 查询该类型所有套餐
     *
     * @param setmeal
     * @return
     */
    @Override
    public R<List<Setmeal>> list(Setmeal setmeal) {
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Setmeal::getCategoryId, setmeal.getCategoryId())
                .eq(Setmeal::getStatus, setmeal.getStatus());
        List<Setmeal> setmeals = setmealMapper.selectList(lqw);
        return R.success(setmeals);
    }

    /**
     * 获取该套餐所有菜品信息包括菜品图片
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public R<List<SetmealDishDto>> getAllDishDto(Long id) {
        LambdaQueryWrapper<SetmealDish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SetmealDish::getSetmealId, id);
        //查询套餐所有菜品
        List<SetmealDish> setmealDishes = setmealDishMapper.selectList(lqw);
        //查询套餐菜品图片
        List<SetmealDishDto> setmealDishDtos = setmealDishes.stream().map(new Function<SetmealDish, SetmealDishDto>() {
            @Override
            public SetmealDishDto apply(SetmealDish setmealDish) {
                SetmealDishDto setmealDishDto = new SetmealDishDto();
                Dish dish = dishMapper.selectById(setmealDish.getDishId());
                //将菜品图片存进去
                setmealDishDto.setImage(dish.getImage());
                BeanUtils.copyProperties(setmealDish, setmealDishDto);
                return setmealDishDto;
            }
        }).collect(Collectors.toList());
        return R.success(setmealDishDtos);
    }
}
