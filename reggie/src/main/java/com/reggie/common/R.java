package com.reggie.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用返回结果
 * @param <T>
 */
@Data
public class R<T> {

    private Integer code;//1 为成功 0或其他为失败

    private T data;

    private String msg;

    private Map map=new HashMap();//动态数据

    //成功操作便调用此静态方法，这样就不用多次重复写
    public static <T> R<T> success(T object,String msg){
        R<T> r = new R<>();
        r.code=1;
        r.data=object;
        r.msg=msg;
        return r;
    }

    public static <T> R<T> success(T object){
        R<T> r = new R<>();
        r.code=1;
        r.data=object;
        return r;
    }

//    public static <T> R<T> success(String msg){
//        R<T> r = new R<>();
//        r.code=1;
//        r.msg=msg;
//        return r;
//    }

    //错误
    public static <T> R<T> error(String msg){
        R<T> r = new R<>();
        r.msg=msg;
        r.code=0;
        return r;
    }

    public R<T> add(String key,Object value){
        this.map.put(key,value);
        return this;
    }
}
