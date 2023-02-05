package com.reggie.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.reggie.common.R;
import com.reggie.dto.OrdersDto;
import com.reggie.entity.Orders;
import com.reggie.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @GetMapping("/page")
    public R<IPage<Orders>> page(Long page, Long pageSize, String number, String beginTime, String endTime){
        R<IPage<Orders>> iPageR = ordersService.page(page, pageSize,number, beginTime, endTime);
        return iPageR;
    }

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        return ordersService.submit(orders);
    }

    @GetMapping("/userPage")
    public R<IPage<OrdersDto>> userPage(Long page,Long pageSize){
        R<IPage<OrdersDto>> iPageR = ordersService.pageByUserId(page, pageSize);
        return iPageR;
    }

    @PutMapping
    public R<String> update(@RequestBody Orders orders){
        return ordersService.update(orders);
    }
}
