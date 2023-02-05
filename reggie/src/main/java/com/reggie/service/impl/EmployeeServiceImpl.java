package com.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.common.R;
import com.reggie.entity.Employee;
import com.reggie.exception.BusinessException;
import com.reggie.exception.SystemException;
import com.reggie.mapper.EmployeeMapper;
import com.reggie.service.EmployeeService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 登录
     *
     * @param request
     * @param employee
     * @return
     */
    @Override
    public R<Employee> selectByUsernamePassword(HttpServletRequest request, Employee employee) {
        //进行md5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //先判断是否存在该用户名（已建立索引，先只判断用户名可减轻数据库压力）
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<Employee>();
        lqw.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeMapper.selectOne(lqw);
        //不存在该用户名
        if (emp == null) {
            return R.error("用户名不存在");
        }
        //密码不一致
        if (!emp.getPassword().equals(password)) {
            return R.error("密码错误");
        }
        //该账户已被封
        if (emp.getStatus() == 0) {
            return R.error("该账号已禁用");
        }
        //将id存进session
        HttpSession session = request.getSession();
        session.setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @Override
    public R<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("employee");
        return R.success("退出成功");
    }

    /**
     * 添加员工
     *
     * @param request
     * @param employee
     * @return
     */
    @Override
    public R<String> addEmployee(HttpServletRequest request, Employee employee) {
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));//初始密码md5加密
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<Employee>();

        try {
            int row = employeeMapper.insert(employee);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                throw new BusinessException("用户名:" + employee.getUsername() + " 已存在", e);
            } else {
                throw new SystemException("添加员工操作失败，请重试", e);
            }

        }
        //msg写不写无所谓
        return R.success("成功");
    }

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public R<IPage<Employee>> getAllByPage(int page, int pageSize, String name) {
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<Employee>();
        //判断是搜索分页还是查询所有分页
        lqw.like(Strings.isNotEmpty(name), Employee::getName, name != null ? name.trim() : null);
        IPage<Employee> iPage = new Page(page, pageSize);
        iPage = employeeMapper.selectPage(iPage, lqw);
        if(iPage.getRecords().isEmpty()){
            throw new SystemException("查询到数据库信息为空");
        }
        return R.success(iPage);
    }

    /**
     * 修改账号状态
     * @param employee
     * @return
     */
    @Override
    public R<String> updateStatus(HttpServletRequest request,Employee employee) {
        Long updateUser = (Long) request.getSession().getAttribute("employee");
        //判断修改状态账号是否为当前登录账号
        if (employee.getId() != updateUser) {
            try {
                employeeMapper.updateById(employee);
            } catch (RuntimeException e) {
                throw new SystemException("修改状态失败", e);
            }
        }else {
            return R.error("不可以修改当前登录账号状态");
        }

        return R.success("修改状态成功");
    }

    /**
     * 查询要修改的用户的信息
     * @param id
     * @return
     */
    @Override
    public R<Employee> getEmployeeById(Long id) {
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<Employee>();
        lqw.eq(id!=0,Employee::getId,id);
        Employee emp = employeeMapper.selectOne(lqw);
        if(emp==null){
            throw new SystemException("没有查询到该员工信息");
        }
        return R.success(emp);
    }

    /**
     * 修改账号信息
     * @param request
     * @param employee
     * @return
     */
    @Override
    public R<Employee> update(HttpServletRequest request, Employee employee) {
        Long id = (Long) request.getSession().getAttribute("employee");
        Employee emp=null;
        //管理员不可修改信息
        if (employee.getId() != 1) {
            employee.setUpdateUser(id);
            employee.setUpdateTime(LocalDateTime.now());
            int row=0;
            try {
                row=employeeMapper.updateById(employee);
                //修改的是当前登录账号
                if(Objects.equals(employee.getId(), id)){
                    emp = employeeMapper.selectById(employee.getId());//更新浏览器用户信息
                }
            } catch (RuntimeException e) {
                throw new SystemException("修改操作出现未知错误",e);
            }
            if(row==0){
                throw new SystemException("用户信息修改失败");
            }
        }else {
            throw new BusinessException("管理员信息不可修改");
        }
        return R.success(emp,"成功");
    }


}

