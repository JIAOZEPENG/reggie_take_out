package com.buba.comtroller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/employee")
public class EmployeeController {

    @GetMapping("/test")
    public String test(){
        return "测试";
    }
}
