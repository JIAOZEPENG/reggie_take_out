package com.buba.comtroller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buba.pojo.Employee;
import com.buba.service.impl.EmployeeServiceImpl;
import com.buba.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl employeeService;

    //登录
    @PostMapping ("/login")
    public R<Employee> login(HttpServletRequest request,@RequestBody Employee employee){

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

        //该账户是否被冻结
        if (!emp.getStatus().equals(1)){
            return R.error("该账户已被封禁");
        }

        //密码正确将用户id存入Session
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    //退出登录
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        //清楚session中的用户信息
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    //添加
    @PostMapping
    private R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        log.info("新增员工，员工信息：{}"+employee.toString());

        //加密密码
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        //获取登录者id
        Long empId = (Long) request.getSession().getAttribute("employee");
        try {
            employeeService.save(employee);
            return R.success("添加成功");
        } catch (Exception e) {
            System.out.println(e);
            return R.error("添加失败，");
        }
    }

    //分页查询
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {

        //构造分页构造器
        Page pageInfo=new Page(page,pageSize);

        //构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper();
        //添加过滤条件
        queryWrapper.like(!StringUtils.isEmpty(name),Employee::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        //执行查询
        employeeService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    //修改员工
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
        //获取登录者id
        Long empId = (Long) request.getSession().getAttribute("employee");
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(empId);
        employeeService.updateById(employee);

        return R.success("员工信息修改成功");
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable String id){
        log.info("根据id查询");
        Employee employee = employeeService.getById(id);
        if (employee != null){
            return R.success(employee);
        }
        return R.error("没有查询到该用户信息");
    }
}
