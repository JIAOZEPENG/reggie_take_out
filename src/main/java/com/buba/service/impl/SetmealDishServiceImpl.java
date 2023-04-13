package com.buba.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.buba.mapper.SetmealDishMapper;
import com.buba.mapper.SetmealMapper;
import com.buba.pojo.Setmeal;
import com.buba.pojo.SetmealDish;
import com.buba.service.SetmealDishService;
import com.buba.service.SetmealService;
import org.springframework.stereotype.Service;


@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {
}
