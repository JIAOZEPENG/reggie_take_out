package com.buba.pojo.dto;

import com.buba.pojo.OrderDetail;
import com.buba.pojo.Orders;
import lombok.Data;
import java.util.List;

@Data
public class OrdersDto extends Orders {

    private String userName;

    private String phone;

    private String address;

    private String consignee;

    private List<OrderDetail> orderDetails;

    private Integer sumNum;
	
}
