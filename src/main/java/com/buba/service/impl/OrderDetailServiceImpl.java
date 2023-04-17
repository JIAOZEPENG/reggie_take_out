package com.buba.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.buba.mapper.OrderDetailMapper;
import com.buba.pojo.OrderDetail;
import com.buba.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}