package com.reggie.controller;

import com.reggie.common.R;
import com.reggie.entity.User;
import com.reggie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpServletRequest request){
        R<String> r = userService.senMsg(user, request);
        return r;
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpServletRequest request){
        R<User> login = userService.login(map, request);
        return login;
    }

    @PostMapping("/loginout")
    public R<String> loginOut(HttpServletRequest request){
        R<String> r = userService.logOut(request);
        return r;
    }

    @GetMapping("/getUser")
    public R<User> getUser(){
        R<User> user = userService.getUser();
        return user;
    }
}
