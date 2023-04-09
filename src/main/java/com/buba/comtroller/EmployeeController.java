package com.buba.comtroller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.buba.pojo.Employee;
import com.buba.utils.R;
import com.buba.service.impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl employeeService;

    //登录
    @PostMapping ("/login")
    public R<Employee> login(HttpServletRequest request,Employee employee){

        //1、将页面提交的密码进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2、根据页面提交的用户名来查数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        //3.是否存在该用户
        if (emp == null){
            return R.error("该用户不存在");
        }

        //存在校验密码
        if (!emp.getPassword().equals(password)){
            return R.error("密码错误");
        }
        //密码正确将用户id存入Session
        request.setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    //退出登录
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        //清楚session中的用户信息
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }
}
