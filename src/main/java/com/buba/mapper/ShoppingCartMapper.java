package com.buba.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.buba.pojo.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

}
