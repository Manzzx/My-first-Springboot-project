package com.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.common.BaseContext;
import com.reggie.common.R;
import com.reggie.dto.OrdersDto;
import com.reggie.entity.*;
import com.reggie.exception.BusinessException;
import com.reggie.exception.SystemException;
import com.reggie.mapper.AddressBookMapper;
import com.reggie.mapper.OrderDetailMapper;
import com.reggie.mapper.OrdersMapper;
import com.reggie.mapper.UserMapper;
import com.reggie.service.AddressBookService;
import com.reggie.service.OrdersService;
import com.reggie.service.ShoppingCartService;
import com.reggie.service.UserService;
import jdk.nashorn.internal.ir.LiteralNode;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AddressBookMapper addressBookMapper;
    @Autowired
    private AddressBookService addressBookService;

    /**
     * 分页可带条件查询
     *
     * @param currentPage
     * @param pageSize
     * @param number
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public R<IPage<Orders>> page(Long currentPage, Long pageSize, String number, String beginTime, String endTime) {
        IPage<Orders> page = new Page(currentPage, pageSize);
        LambdaQueryWrapper<Orders> lqw = new LambdaQueryWrapper<>();
        lqw.like(Strings.isNotEmpty(number), Orders::getNumber, number != null ? number.trim() : null);
        lqw.between(Strings.isNotEmpty(beginTime), Orders::getOrderTime, beginTime, endTime);
        try {
            page = ordersMapper.selectPage(page, lqw);
        } catch (Exception e) {
            throw e;
        }
        return R.success(page);
    }

    /**
     * 提交订单
     *
     * @param orders
     * @return
     */
    @Override
    @Transactional
    public R<String> submit(Orders orders) {
        //订单总价
        AtomicInteger amount = new AtomicInteger(0);//保证线程安全，原子性
        //获取当前用户id
        Long userId = BaseContext.getCurrentId();
        //查询购物车数据
        R<List<ShoppingCart>> allByUserIdR = shoppingCartService.getAllByUserId();
        List<ShoppingCart> cartList = allByUserIdR.getData();
        //防止绕过前端校验发送异常请求
        if (cartList.isEmpty()) {
            throw new BusinessException("购物车为空无法生成订单");
        }
        //查询用户信息
        User user = userMapper.selectById(userId);
        ////获取地址，查询用户地址信息
        AddressBook addressBook = addressBookMapper.selectById(orders.getAddressBookId());
        if (addressBook == null) {
            throw new BusinessException("地址为空无法生成订单");
        }

        long orderId = IdWorker.getId();//生成订单id
        //设置订单详情属性值
        List<OrderDetail> orderDetails = cartList.stream().map(shoppingCart -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setName(shoppingCart.getName());
            orderDetail.setNumber(shoppingCart.getNumber());
            orderDetail.setImage(shoppingCart.getImage());
            orderDetail.setOrderId(orderId);
            orderDetail.setDishId(shoppingCart.getDishId());
            orderDetail.setSetmealId(shoppingCart.getSetmealId());
            orderDetail.setDishFlavor(shoppingCart.getDishFlavor());
            orderDetail.setAmount(shoppingCart.getAmount());//商品原价
            //计算一样商品价格
            amount.addAndGet(shoppingCart.getAmount().multiply(new BigDecimal(shoppingCart.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());

        //设置订单属性值
        orders.setNumber(String.valueOf(orderId));
        orders.setStatus(2);//代派送
        orders.setUserId(userId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setAmount(new BigDecimal(amount.get()));//填充总金额
        orders.setPhone(addressBook.getPhone());
        orders.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
                + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
                + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
                + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee());

        //插入订单表
        ordersMapper.insert(orders);
        //遍历插入订单详情表
        orderDetails.forEach(orderDetail -> orderDetailMapper.insert(orderDetail));

        //清空购物车
        R<String> r = shoppingCartService.deletedAllByUserId();
        return r;
    }

    /**
     * 用户订单分页查询
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public R<IPage<OrdersDto>> pageByUserId(Long currentPage, Long pageSize) {
        //前端返回数据对象
        IPage<OrdersDto> page = new Page<>();

        IPage<Orders> iPage = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<Orders> lqw = new LambdaQueryWrapper<>();
        //根据下单时间降序展示
        lqw.eq(Orders::getUserId, BaseContext.getCurrentId())
                .orderByDesc(Orders::getOrderTime);
        iPage = ordersMapper.selectPage(iPage, lqw);
        //查询每个订单的订单详情,list<order>转List<OrdersDto>对象
        List<OrdersDto> orderDetails = iPage.getRecords().stream().map(new Function<Orders, OrdersDto>() {
            @Override
            public OrdersDto apply(Orders orders) {
                OrdersDto ordersDto = new OrdersDto();
                LambdaQueryWrapper<OrderDetail> qw = new LambdaQueryWrapper<>();
                qw.eq(OrderDetail::getOrderId, new Long(orders.getNumber()));
                //当前订单的详情
                List<OrderDetail> orderDetails = orderDetailMapper.selectList(qw);
                //copy到Dot对象
                BeanUtils.copyProperties(orders, ordersDto, "orderDetails");
                ordersDto.setOrderDetails(orderDetails);
                return ordersDto;
            }
        }).collect(Collectors.toList());
        //copy page对象
        BeanUtils.copyProperties(iPage, page, "records");
        page.setRecords(orderDetails);
        return R.success(page);
    }

    /**
     * 更新订单
     * @param orders
     * @return
     */
    @Override
    public R<String> update(Orders orders) {
        int i = ordersMapper.updateById(orders);
        if (i==0){
            throw new SystemException("订单修改失败");
        }
        return R.success("成功");
    }
}
