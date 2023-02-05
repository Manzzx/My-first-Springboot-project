package com.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reggie.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {
    /**
     * 查询已经删除过的套餐
     * @param name
     * @return
     */
    @Select("select * from setmeal where is_deleted=1 and name=#{name}")
    Setmeal selectSetmealIsDeletedByName(String name);

    @Update("update setmeal set category_id=#{categoryId},name=#{name}," +
            "price=#{price},status=#{status},description=#{description}," +
            "image=#{image},create_time=#{createTime},update_time=#{updateTime}," +
            "create_user=#{createUser},update_user=#{updateUser},is_deleted=0 where id=#{id}")
    int updateIsDeletedById(Setmeal setmeal);
}
