package com.reggie.dto;


import com.reggie.entity.OrderDetail;
import com.reggie.entity.Orders;
import lombok.Data;
import java.util.List;

/**
 * 页面展示对象
 */
@Data
public class OrdersDto extends Orders {

    private List<OrderDetail> orderDetails;

	
}
