package com.buba.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.buba.pojo.Dish;
import com.buba.pojo.dot.DishDto;

public interface DishService extends IService<Dish> {
    public void saveWithFlavor(DishDto dishDto);
}
