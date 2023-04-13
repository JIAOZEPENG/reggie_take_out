package com.buba.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.buba.pojo.Dish;
import com.buba.pojo.dto.DishDto;

public interface DishService extends IService<Dish> {
    public void saveWithFlavor(DishDto dishDto);

    DishDto getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDto dishDto);

    boolean removeById(String id);
}
