package com.reggie;

import com.reggie.dto.DishDto;
import com.reggie.mapper.DishMapper;
import com.reggie.mapper.ShoppingCartMapper;
import com.reggie.utils.CommentUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Random;

@SpringBootTest
class ReggieApplicationTests {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;


    @Test
    void contextLoads() {
//        Long page=2L;
//        Long size=8L;
//        Long start=(page-1)*size;
//        List<DishDto> dishDto = dishMapper.selectDishDtoByDishJoinCategory(start, size);
//        System.out.println(dishDto);

//        for (int i=0 ;i<100;i++) {
//            Integer code = new Random().nextInt(9999);
//            System.out.println("====="+code);
//        }

//        String a="常温,不要蒜,微辣";
//        String b="不要香菜,微辣,去冰,常温";
        String a="常温,不要蒜,微辣";
//        String b="微辣,常温,不要蒜";
//        System.out.println(CommentUtils.equalsFlavor(a, b));
        System.out.println(CommentUtils.sortFlavor(a));
        System.out.println(shoppingCartMapper.selectList(null));

    }

}
