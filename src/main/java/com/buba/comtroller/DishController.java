package com.buba.comtroller;

import com.buba.pojo.dot.DishDto;
import com.buba.service.DishFlavorService;
import com.buba.service.DishService;
import com.buba.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorService dishFlavorService;

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        try {
            dishService.saveWithFlavor(dishDto);
        } catch (Exception e) {
            e.printStackTrace();
            return R.success("新增菜品失败");
        }
        return R.success("新增菜品成功");
    }
}
