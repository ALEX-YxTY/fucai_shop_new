package com.meishipintu.fucaiShopNew.utils;

import android.content.Context;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static boolean isNullOrEmpty(String s) {
        return s == null || "".equals(s.trim().replace(" ", ""));
    }

    public static String NullToEmpty(String s) {
        return s == null ? "无" : s;
    }

    //反相序每四位添加空格，适用于电话号码等
    public static String stringWithSpaceReverse(String s) {
        if (s == null) {
            return "";
        }
        s = s.replace(" ", "");
        if (s.length() <= 3) {
            return s;
        }
        int length = s.length();
        StringBuffer result = new StringBuffer();

        int index = 0;      //标记空格数，第一个空格第三位，后面每4位一个空格
        int start = 0;      //标记每次开始位数
        for(int i=1;i<length;i++) {
            if (i % (3 + 4 * index) == 0 ) {
                result.append(s.substring(start, i));
                result.append(" ");
                index++;
                start = i;
            }
        }
        result.append(s.substring(start));
        return result.toString();
    }

    //正向每四位加空格，适用于卡券号等
    public static String stringWithSpace(String s) {
        if (s == null) {
            return "";
        }
        s = s.replace(" ", "");
        if (s.length() < 4) {
            return s;
        } else {
            StringBuilder result = new StringBuilder();
            int start = 0;
            for(int i=1;i<s.length();i++) {
                if (i % 4 == 0) {
                    result.append(s.substring(start, i));
                    result.append(" ");
                    start = i;
                }
                if (i == s.length() - 1) {
                    result.append(s.substring(start));
                }
            }
            return result.toString();
        }
    }

    public static String delTheSpace(String s) {
        return s.replace(" ", "");
    }

    public static String decimalFormat(String num) {
        DecimalFormat df = new DecimalFormat("00000000");
        return df.format(Integer.parseInt(num));
    }

    public static boolean isNullOrEmptOrZero(String money) {
        return isNullOrEmpty(money) || Integer.parseInt(money) == 0;
    }

    //数据转格式，保留两位小数，不足补0，多余四舍五入
    public static String floatFormat(float num) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);   //多余位数四舍五入
        return df.format(num);
    }

    //数据转格式，保留两位小数，不足补0，多余四舍五入
    public static String floatFormat(double num) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);   //多余位数四舍五入
        return df.format(num);
    }

    public static String getMoileHid(String mobile) {

        return mobile.substring(0, 2) + "****" + mobile.substring(7);
    }

    public static final String REGEX_USER_NAME = ".{2,32}";
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9_\\.]{6,16}$";
    public static final String REGEX_EMAIL = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
    public static final String REGEX_RECOGNISED_SHOP = "\\[.*?\\]\\[(\\d*)\\]";
    public static final String SCAN_RESULT_TEXT = "scan_result_text";
    public static final String REGEX_NUM = "^[0-9]{11,11}$";
    public static final int SHARE_PIC_BLOG_LENGTH = 140;

    public static String formatSn(String sn){
        StringBuilder s;
        s = new StringBuilder(sn);

        for(int i = 4; i < s.length(); i += 5){
            s.insert(i, " ");
        }
        return s.toString();
    }
    public static boolean checkUserName(String inputString) {
        Pattern pattern = Pattern.compile(REGEX_USER_NAME);
        Matcher matcher = pattern.matcher(inputString);
        return matcher.matches();
    }

    public static boolean checkLoginAccount(String inputString) {
        boolean resCheckEmail = checkEmail(inputString);
        return resCheckEmail;
    }

    public static boolean checkPassword(String inputString) {
        Pattern pattern = Pattern.compile(REGEX_PASSWORD);
        Matcher matcher = pattern.matcher(inputString);
        return matcher.matches();
    }

    public static boolean checkEmail(String inputString) {
        Pattern pattern = Pattern.compile(REGEX_EMAIL);
        Matcher matcher = pattern.matcher(inputString);
        return matcher.matches();
    }

    public static boolean checkPhone(String inputString) {
        Pattern pattern = Pattern.compile(REGEX_NUM);
        Matcher matcher = pattern.matcher(inputString);
        return matcher.matches();
    }

    public static String genAndTrancateShareBlogText(Context ctx, String head, String tail){
        int hLen = head.length();
        int tLen = tail.length();

        //濡傛灉鍚庨潰閮ㄥ垎閮借秴杩囦簡闀垮害锛岄偅鍚庨潰閮ㄥ垎骞茶剢涓嶈浜�
        if (tLen > SHARE_PIC_BLOG_LENGTH) {
            if (hLen > SHARE_PIC_BLOG_LENGTH) {
                return head.substring(0, hLen - 3) + "...";
            }
            return (head + tail).substring(0, hLen - 3) + "...";
        }else {
            if ( (SHARE_PIC_BLOG_LENGTH - tLen ) > hLen) {
                return head + " " + tail;
            }else {
                return head.substring(0, SHARE_PIC_BLOG_LENGTH - tLen -3) + "..." + tail;
            }
        }

    }

    public static int getTextSize(String str) {
        if (str.length() < 120) {
            return 18;
        } else if (str.length() < 220) {
            return 15;
        } else {
            return 12;
        }
    }

    public static String getPriceString(int price){
        if ((price % 10) > 0){
            return String.format("%.2f", (float)price/100);
        }else if ((price % 100)>0){
            return String.format("%.1f", (float)price/100);
        }else {
            return price/100 + "";
        }
    }

    /**
     * 鏍规嵁鎵嬫満鐨勫垎杈ㄧ巼浠� dp 鐨勫崟浣� 杞垚涓� px(鍍忕礌)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 鏍规嵁鎵嬫満鐨勫垎杈ㄧ巼浠� px(鍍忕礌) 鐨勫崟浣� 杞垚涓� dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
