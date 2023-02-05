package com.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reggie.dto.DishDto;
import com.reggie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
    /**
     * 检查有没有之前删除过的菜品
     *
     * @param dish
     * @return
     */
    @Select("select * from dish where is_deleted=1 and name=#{name}")
    Dish selectByIsDeletedAndName(Dish dish);

    /**
     * 更新isDeleted状态
     */
    @Update("UPDATE dish SET name=#{name},category_id=#{categoryId}, price=#{price}, image=#{image}," +
            " description=#{description}, create_time=#{createTime}," +
            "update_time=#{updateTime}, create_user=#{createUser}, " +
            "update_user=#{updateUser}, is_deleted=0 WHERE id=#{id}")
    int updateIsDeleted(Dish dish);

    /**
     * 分页查询返回给dto对象
     */
    @Select("select dish.id, dish.name,category.name as categoryName,price,image,status,dish.update_time " +
            "from dish join category on dish.category_id=category.id" +
            " where dish.is_deleted=0 LIMIT #{start},#{size}")
    List<DishDto> selectDishDtoByDishJoinCategory(@Param("start") Long start,
                                                  @Param("size") Long size);

    /**
     * 通过增加名称条件分页查询返回给dto对象
     */
    @Select("select dish.id, dish.name,category.name as categoryName,price,image,status,dish.update_time " +
            "from dish join category on dish.category_id=category.id " +
            "where dish.is_deleted=0 and dish.name like CONCAT('%', #{name}, '%') LIMIT #{start},#{size}")
    List<DishDto> selectDishDtoByDishJoinCategoryWithName(@Param("name") String name,
                                                          @Param("start") Long start,
                                                          @Param("size") Long size);

    /**
     * 通过dishId查询返回给dto对象
     */
    @Select("select dish.id, dish.name,category.id as categoryId, category.name as categoryName," +
            "price,image,status,dish.update_time " +
            "from dish join category on dish.category_id=category.id " +
            "where dish.id=#{id}")
    DishDto selectDishDtoByDishJoinCategoryWithId(Long id);


    /**
     * 将图片设为null
     */
    @Update("UPDATE dish SET image='null',is_deleted=0 WHERE id=#{id}")
    int updateDishImgToNull(Long id);

    /**
     * 通过增加categoryId条件查询所有该菜品类型的菜品返回给dish对象
     */
    @Select("select * from dish where is_deleted=0 and category_id=#{categoryId} and status=1 order by update_time")
    List<Dish> selectDishByCategoryId(@Param("categoryId") Long categoryId);

}
