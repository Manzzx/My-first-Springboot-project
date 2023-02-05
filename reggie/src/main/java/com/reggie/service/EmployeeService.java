package com.reggie.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.reggie.common.R;
import com.reggie.entity.Employee;

import javax.servlet.http.HttpServletRequest;

public interface EmployeeService {

    R<Employee> selectByUsernamePassword(HttpServletRequest request, Employee employee);

    R<String> logout(HttpServletRequest request);

    R<String> addEmployee(HttpServletRequest request,Employee employee);

    R<IPage<Employee>> getAllByPage(int page, int pageSize, String name);

    R<String> updateStatus(HttpServletRequest request,Employee employee);

    R<Employee> getEmployeeById(Long id);

    R<Employee> update(HttpServletRequest request,Employee employee);

}
