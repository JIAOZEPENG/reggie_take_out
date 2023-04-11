package com.buba.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.buba.mapper.DishFlavorMapper;
import com.buba.pojo.DishFlavor;
import com.buba.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
