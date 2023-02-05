package com.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reggie.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    /**
     * 检查有没有之前删除过的
     * @param category
     * @return
     */
    @Select("select * from category where is_deleted=1 and name=#{name}")
    Category selectByIsDeletedAndName(Category category);
    /**
     * 更新isDeleted状态
     */
    @Update("UPDATE category SET type=#{type}, name=#{name}, sort=#{sort}, create_time=#{createTime}, " +
            "update_time=#{updateTime}, create_user=#{createUser}, update_user=#{updateUser}, " +
            "is_deleted=0 WHERE id=#{id}")
    int updateIsDeleted(Category category);
}
