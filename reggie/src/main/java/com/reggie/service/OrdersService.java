package com.reggie.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.common.R;
import com.reggie.dto.OrdersDto;
import com.reggie.entity.Orders;
import com.reggie.entity.ShoppingCart;

import java.time.LocalDateTime;

public interface OrdersService {

    R<IPage<Orders>> page(Long page, Long pageSize, String number, String beginTime, String endTime);

    R<String> submit(Orders orders);

    R<IPage<OrdersDto>> pageByUserId(Long currentPage,Long pageSize);

    R<String> update(Orders orders);
}
