package com.buba.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.buba.mapper.EmployeeMapper;
import com.buba.pojo.Employee;
import com.buba.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
