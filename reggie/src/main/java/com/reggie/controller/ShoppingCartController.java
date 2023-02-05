package com.reggie.controller;

import com.reggie.common.R;
import com.reggie.entity.ShoppingCart;
import com.reggie.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart, HttpServletRequest request){
        System.out.println("=========sssss========="+shoppingCart);
        R<ShoppingCart> r = shoppingCartService.add(shoppingCart,request);
        return r;
    }

    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){
        return shoppingCartService.getAllByUserId();
    }

    @PostMapping("/sub")
    public R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart){
        return shoppingCartService.subDishOrSetmeal(shoppingCart);
    }

    @DeleteMapping("/clean")
    public R<String> clean(){
        return shoppingCartService.deletedAllByUserId();
    }

}
