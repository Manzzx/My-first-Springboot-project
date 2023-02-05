package com.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.Base64;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
    //验证为什么mp修改status，updateUser为空也set updateUser
    @Update("update employee set status=#{status} where id=#{id}")
    int updateStatus(Employee employee);
}
