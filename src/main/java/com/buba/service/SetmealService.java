package com.buba.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.buba.pojo.Setmeal;
import com.buba.pojo.dto.SetmealDto;

public interface SetmealService extends IService<Setmeal> {
    public void saveWithDish(SetmealDto setmealDto);

    SetmealDto getByIdWithDish(Long id);

    void updateWithDish(SetmealDto setmealDto);
}