package com.meishipintu.fucaiShopNew.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Administrator on 2017/4/12.
 * <p>
 * 主要功能：
 */

public class NumberUtils {

    public static String formatNum(int num) {
        if (num < 0) {
            return "00";
        }
        if (num < 10) {
            return "0" + num;
        } else {
            return "" + num;
        }
    }

    public static String formatNum(String num) {
        return formatNum(Integer.parseInt(num));
    }

    //转为8位数，不足补0
    public static String decimalFormat(int num) {
        DecimalFormat df = new DecimalFormat("00000000");
        return df.format(num);
    }

    //数据转格式，保留两位小数，不足补0，多余四舍五入
    public static String floatFormat(float num) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);   //多余位数四舍五入
        return df.format(num);
    }

    //A组合算法
    public static int combination(int upx, int downy) {
        int result = 1;
        if (upx > (downy / 2)) {
            upx = downy - upx;
        }
        for(int i=0;i<upx;i++) {
            result = result * (downy - i) / (i + 1);
        }
        return result;
    }

    //List to String join with \,\
    public static String list2String(List<Integer> select) {
        StringBuilder builder = new StringBuilder();
        for (Integer item : select) {
            builder.append(NumberUtils.formatNum(item));
            builder.append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }
}
