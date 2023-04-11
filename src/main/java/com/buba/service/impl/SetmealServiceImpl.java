package com.buba.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.buba.mapper.SetmealMapper;
import com.buba.pojo.Setmeal;
import com.buba.service.SetmealService;
import org.springframework.stereotype.Service;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
}