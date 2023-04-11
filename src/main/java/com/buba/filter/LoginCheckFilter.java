package com.buba.filter;

import com.alibaba.fastjson.JSONObject;
import com.buba.comtroller.EmployeeController;
import com.buba.utils.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//@WebFilter(filterName = "LoginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER=new AntPathMatcher();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        //要放行的请求
        String[] url = new String[]{
                "/employee/login",
                "/employee/logout"
        };

        //获取本次请求的路径
        String requestURL = request.getRequestURI();
        boolean check = check(url, requestURL);
        if (check){
            //放行
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        //判断是否登录
        if (request.getSession().getAttribute("employee") == null){
            servletResponse.setCharacterEncoding("UTF-8");
            servletResponse.setContentType("application/json; charset=utf-8");
            PrintWriter out = servletResponse.getWriter();
            JSONObject res = new JSONObject();
            res.put("msg", "未登录");
            res.put("success", "false");
            out.append(res.toString());
            return;
        }else {
            log.info("用户已登录，用户id为：{}", request.getSession().getAttribute("employee"));

            Long empId= (Long) request.getSession().getAttribute("employee");

            BaseContext.setCurrentId(empId);

            filterChain.doFilter(request, response);
            return;
        }

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    public boolean check(String[] urls,String requestURI){
        for (String url : urls) {
            //路径匹配器
            boolean match = PATH_MATCHER.match(url, requestURI);
            if(match==true){
                return true;
            }
        }
        return  false;
    }
}
