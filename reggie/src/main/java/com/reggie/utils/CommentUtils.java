package com.reggie.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;


import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * 工具类
 */
public class CommentUtils {
    /**
     * 判断两个中文数组字符串是否相同（不分顺序）
     */
    public static boolean equalsFlavor(String exitFlavor, String newFlavor) {
        String[] exitFlavors = exitFlavor.split(",");
        String[] newFlavors = newFlavor.split(",");
        System.out.println(Arrays.toString(exitFlavors));
        //先定义中文排序器
        Comparator c = Collator.getInstance(Locale.CHINA);
        //根据中文规则排序
        Arrays.sort(exitFlavors, c);
        Arrays.sort(newFlavors, c);
        System.out.println(Arrays.toString(exitFlavors));
        return Arrays.equals(exitFlavors, newFlavors);
    }

    /**
     * 对中文字符串排序（用于数据库口味判断）
     * @param flavor
     * @return
     */
    public static String sortFlavor(String flavor) {
        String[] sFlavors = flavor.split(",");
        //先定义中文排序器
        Comparator c = Collator.getInstance(Locale.CHINA);
        //根据中文规则排序
        Arrays.sort(sFlavors, c);
        //数组转list去掉[]
        List<String> flavors = Arrays.asList(sFlavors);
        //去除空格
        flavor = StringUtils.strip(flavors.toString(), "[]").replaceAll(" ", "");
        return flavor;
    }
}
