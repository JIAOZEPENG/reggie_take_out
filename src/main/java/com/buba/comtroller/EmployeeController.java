package com.buba.comtroller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buba.pojo.Employee;
import com.buba.utils.R;
import com.buba.service.impl.EmployeeServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl employeeService;

    private Logger log = Logger.getLogger(EmployeeController.class);

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

    //添加
    @PostMapping
    private R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        log.info("新增员工，员工信息：{}"+employee.toString());

        //加密密码
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        //获取登录者id
        Long empId = (Long) request.getSession().getAttribute("employee");
        employee.setCreateUser(1l);
        employee.setUpdateUser(1l);

        //设置id

        employee.setId(1l);
        UUID uuid = UUID.randomUUID();
        long mostSigBits = uuid.getMostSignificantBits();
        long leastSigBits = uuid.getLeastSignificantBits();
        System.out.println(mostSigBits ^ leastSigBits);
        employee.setId(mostSigBits ^ leastSigBits);
        try {
            employeeService.save(employee);
            return R.success("添加成功");
        } catch (Exception e) {
            System.out.println(e);
            log.error(e);
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
}
