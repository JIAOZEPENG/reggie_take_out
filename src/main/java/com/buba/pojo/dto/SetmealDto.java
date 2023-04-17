package com.buba.pojo.dto;

import com.buba.pojo.Setmeal;
import com.buba.pojo.SetmealDish;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {
    private List<SetmealDish> setmealDishes = new ArrayList<>();

    private String categoryName;
}
