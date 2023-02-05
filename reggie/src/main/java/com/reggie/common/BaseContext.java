package com.reggie.common;

/**
 * 基于ThreadLocal封装工具类，用户保存和获取当前登录用户id,用于请求内共享id
 * x类调用这个工具类，处理x类的线程会开辟一个自己的ThreadLocal来存储数据（作用域为该线程内）
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal=new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
