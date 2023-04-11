package com.buba.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.buba.mapper.DishMapper;
import com.buba.pojo.Dish;
import com.buba.service.DishService;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
}
