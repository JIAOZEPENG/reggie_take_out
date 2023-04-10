package com.buba.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.buba.mapper.CategoryMapper;
import com.buba.pojo.Category;
import com.buba.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServicelmpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
