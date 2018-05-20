package com.developer.gram.easybilibili.util;

/**
 * @author zzq  作者 E-mail:   soleilyoyiyi@gmail.com
 * @date 创建时间：2017/5/21 23:11
 * 描述:将字符串数字转换成以万为单位的字符串数字
 */
public class NumberUtils {
    public static String format(String num) {
        Integer integer = Integer.valueOf(num);
        if (integer < 10000) {
            return String.valueOf(num);
        }
        String unit = "万";
        double newNum = integer / 10000.0;
        String numStr = String.format("%.1f" , newNum);
        return numStr + unit;
    }

    public static String format(int num) {
        if (num < 100000) {
            return String.valueOf(num);
        }
        String unit = "万";
        double newNum = num / 10000.0;
        String numStr = String.format("%." + 1 + "f", newNum);
        return numStr + unit;
    }
}
