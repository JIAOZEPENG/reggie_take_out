package com.buba.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.buba.pojo.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
