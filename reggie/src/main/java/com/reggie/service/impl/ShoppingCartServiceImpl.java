package com.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.reggie.common.BaseContext;
import com.reggie.common.R;
import com.reggie.entity.ShoppingCart;
import com.reggie.exception.SystemException;
import com.reggie.mapper.ShoppingCartMapper;
import com.reggie.service.ShoppingCartService;
import com.reggie.utils.CommentUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;


    /**
     * 将商品添加购物车
     *
     * @param shoppingCart
     * @return
     */
    @Override
    @Transactional
    public R<ShoppingCart> add(ShoppingCart shoppingCart, HttpServletRequest request) {
        //获取用户id，对该用户购物车进行添加操作
        Long userId = BaseContext.getCurrentId();
        //设置插入条件
        shoppingCart.setId(null);//清空前端传过来的id，让mp生成
        shoppingCart.setUserId(userId);
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId, userId);
        //判断商品是菜品还是套餐
        try {
            if (shoppingCart.getDishId() != null) {
                //菜品
                String flavor = null;
                //判断是否有口味数据传来，没有就默认口味（只要前端传过来的口味不为空这里就不执行）
                if (shoppingCart.getDishFlavor() == null) {
                    flavor = "默认口味";
                    shoppingCart.setDishFlavor("默认口味");
                } else {
                    //有口味传过来，对口味进行排序再转字符串方便比较
                    flavor = CommentUtils.sortFlavor(shoppingCart.getDishFlavor());
                }

                //是菜品,判断该菜品（同一用户id、dishId和口味为同一商品）是否已经在购物车
                lqw.eq(ShoppingCart::getDishId, shoppingCart.getDishId())
                        .eq(ShoppingCart::getDishFlavor, flavor);
                ShoppingCart dishCart = shoppingCartMapper.selectOne(lqw);
                //查询所有该不分口味菜品添加条件
                LambdaQueryWrapper<ShoppingCart> lqwS = new LambdaQueryWrapper<>();
                lqwS.eq(ShoppingCart::getDishId, shoppingCart.getDishId())
                        .eq(ShoppingCart::getUserId, userId);
                if (dishCart != null) {
                    //同一菜品开始更新
                    //设置更新条件
                    LambdaUpdateWrapper<ShoppingCart> luw = new LambdaUpdateWrapper<>();
                    luw.eq(ShoppingCart::getUserId, userId)
                            .eq(ShoppingCart::getDishId, dishCart.getDishId());
                    luw.set(ShoppingCart::getNumber, dishCart.getNumber() + 1);
                    luw.set(ShoppingCart::getUpdateTime, LocalDateTime.now());
                    luw.eq(ShoppingCart::getDishFlavor, dishCart.getDishFlavor());
                    shoppingCartMapper.update(null, luw);//更新
                    shoppingCart.setId(dishCart.getId());
                    //返回前端菜品数量应该是该用户该菜品所有数量不分口味
                    List<ShoppingCart> shoppingCarts = shoppingCartMapper.selectList(lqwS);
                    shoppingCart.setNumber(0);
                    for (ShoppingCart cart : shoppingCarts) {
                        shoppingCart.setNumber(cart.getNumber() + shoppingCart.getNumber());
                    }
                } else {
                    //不在购物车不是同一菜品,开始插入
//                    shoppingCart.setNumber(1);
                    shoppingCartMapper.insert(shoppingCart);
                    //返回前端菜品数量应该是该用户该菜品所有数量不分口味
                    List<ShoppingCart> shoppingCarts = shoppingCartMapper.selectList(lqwS);
                    shoppingCart.setNumber(0);
                    for (ShoppingCart cart : shoppingCarts) {
                        shoppingCart.setNumber(cart.getNumber() + shoppingCart.getNumber());
                    }
                }
            } else {
                //是套餐,判断该套餐（同一用户id和setmealId为同一商品）是否已经在购物车
                lqw.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
                ShoppingCart setmealCart = shoppingCartMapper.selectOne(lqw);
                if (setmealCart != null) {
                    //购物车已存在该套餐,开始更新
                    LambdaUpdateWrapper<ShoppingCart> luw = new LambdaUpdateWrapper<>();
                    luw.eq(ShoppingCart::getUserId, userId)
                            .eq(ShoppingCart::getSetmealId, setmealCart.getSetmealId());
                    luw.set(ShoppingCart::getNumber, setmealCart.getNumber() + 1);
                    luw.set(ShoppingCart::getUpdateTime, LocalDateTime.now());
                    shoppingCartMapper.update(null, luw);
                    shoppingCart.setId(setmealCart.getId());
                    shoppingCart.setNumber(setmealCart.getNumber() + 1);
                } else {
                    //购物车不存在该套餐,开始插入
                    shoppingCartMapper.insert(shoppingCart);
                    shoppingCart.setNumber(1);
                }
            }
        } catch (Exception e) {
            throw new SystemException("商品加入购物车失败", e);
        }
        return R.success(shoppingCart);
    }

    /**
     * 查询该用户购物车数据
     *
     * @return
     */
    @Override
    public R<List<ShoppingCart>> getAllByUserId() {
        Long userId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId, userId);
        List<ShoppingCart> shoppingCarts = shoppingCartMapper.selectList(lqw);
        return R.success(shoppingCarts);
    }

    /**
     * 删除菜品（通过菜单或者购物车操作）或者套餐
     *
     * @param shoppingCart
     * @return
     */
    @Override
    public R<ShoppingCart> subDishOrSetmeal(ShoppingCart shoppingCart) {
        Long dishId = shoppingCart.getDishId();
        Long userId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId, userId);//删除公共条件
        //判断是删除菜品还是套餐
        if (dishId != null) {
            //删除菜品
            lqw.eq(ShoppingCart::getDishId, dishId);//删除菜品公共条件
            //判断是在菜单操作还是购物车操作
            String dishFlavor = shoppingCart.getDishFlavor();
            if (dishFlavor != null) {
                //购物车操作
                dishFlavor = CommentUtils.sortFlavor(dishFlavor);// 排序让数据库比较
                lqw.eq(ShoppingCart::getDishFlavor, dishFlavor);
                ShoppingCart deletedDish = shoppingCartMapper.selectOne(lqw);
                //判断数量是否为1
                if (deletedDish.getNumber() > 1) {
                    deletedDish.setNumber(deletedDish.getNumber() - 1);
                    shoppingCartMapper.updateById(deletedDish);
                } else {
                    shoppingCartMapper.deleteById(deletedDish.getId());//删除
                }

            } else {
                //菜单操作
                //根据更新时间降序查询
                lqw.orderByDesc(ShoppingCart::getUpdateTime);
                List<ShoppingCart> shoppingCarts = shoppingCartMapper.selectList(lqw);
                //删除最后加入的菜品（包含口味条件）
                ShoppingCart deletedDish = shoppingCarts.get(0);
                //判断数量是否为1
                if (deletedDish.getNumber() > 1) {
                    deletedDish.setNumber(deletedDish.getNumber() - 1);
                    shoppingCartMapper.updateById(deletedDish);
                } else {
                    shoppingCartMapper.deleteById(deletedDish.getId());
                }
            }
            //开始查询购物车所有该商品
            LambdaQueryWrapper<ShoppingCart> qw = new LambdaQueryWrapper<>();
            qw.eq(ShoppingCart::getUserId, userId)
                    .eq(ShoppingCart::getDishId, dishId);
            //返回前端菜品数量应该是该用户该菜品所有数量不分口味
            List<ShoppingCart> dishs = shoppingCartMapper.selectList(qw);
            shoppingCart.setNumber(0);//设置返回数量
            //判断该菜品所有口味是否已经没有了
            if (!dishs.isEmpty()) {
                for (ShoppingCart dish : dishs) {
                    shoppingCart.setNumber(dish.getNumber() + shoppingCart.getNumber());//设置返回数量
                }
            }
        } else {
            //删除套餐
            lqw.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
            ShoppingCart deletedSetmeal = shoppingCartMapper.selectOne(lqw);
            //判断数量是否为1
            if (deletedSetmeal.getNumber() > 1) {
                deletedSetmeal.setNumber(deletedSetmeal.getNumber() - 1);
                shoppingCartMapper.updateById(deletedSetmeal);
                shoppingCart.setNumber(deletedSetmeal.getNumber());//设置返回数量
            } else {
                shoppingCartMapper.deleteById(deletedSetmeal.getId());
                shoppingCart.setNumber(0);//设置返回数量
            }
        }
        return R.success(shoppingCart);
    }

    /**
     * 清空购物车
     *
     * @return
     */
    @Override
    public R<String> deletedAllByUserId() {
        Long userId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        lqw.eq(userId != null, ShoppingCart::getUserId, userId);
        try {
            shoppingCartMapper.delete(lqw);
        } catch (Exception e) {
            throw new SystemException("购物车清空失败", e);
        }
        return R.success("成功");
    }
}
