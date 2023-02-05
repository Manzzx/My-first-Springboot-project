package com.reggie.service;

import com.reggie.common.R;
import com.reggie.entity.ShoppingCart;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ShoppingCartService {

    R<ShoppingCart> add(ShoppingCart shoppingCart, HttpServletRequest request);

    R<List<ShoppingCart>> getAllByUserId();

    R<ShoppingCart> subDishOrSetmeal(ShoppingCart shoppingCart);

    R<String> deletedAllByUserId();
}
