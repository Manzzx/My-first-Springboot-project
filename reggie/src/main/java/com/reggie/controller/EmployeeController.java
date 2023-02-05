package com.reggie.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.reggie.common.R;
import com.reggie.entity.Employee;
import com.reggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        R<Employee> r = employeeService.selectByUsernamePassword(request, employee);
        return r;
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        R<String> r = employeeService.logout(request);
        return r;
    }

    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        R<String> r = employeeService.addEmployee(request, employee);
        return r;
    }

    @GetMapping("/page")
    public R<IPage<Employee>> page(int page, int pageSize, String name){
        R<IPage<Employee>> emps = employeeService.getAllByPage(page, pageSize, name);
        return emps;
    }

    @PutMapping
    public R<String> updateStatus(HttpServletRequest request,@RequestBody Employee employee){
        R<String> r = employeeService.updateStatus(request,employee);
        return r;
    }

    @GetMapping("/{id}")
    public R<Employee> getEmployeeById(@PathVariable Long id){
        R<Employee> emp = employeeService.getEmployeeById(id);
        return emp;

    }

    @PutMapping("/edit")
    public R<Employee> edit(HttpServletRequest request,@RequestBody Employee employee){
        R<Employee> r = employeeService.update(request, employee);
        return r;
    }
}
