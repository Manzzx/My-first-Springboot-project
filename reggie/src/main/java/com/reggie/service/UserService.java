package com.reggie.service;

import com.reggie.common.R;
import com.reggie.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface UserService {

    R<String> senMsg(User user, HttpServletRequest request);

    R<User> login(Map map, HttpServletRequest request);

    R<String> logOut(HttpServletRequest request);

    R<User> getUser();
}
