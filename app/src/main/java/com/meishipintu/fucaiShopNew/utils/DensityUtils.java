package com.meishipintu.fucaiShopNew.utils;

import android.content.Context;

import com.meishipintu.fucaiShopNew.MyApplication;

/**
 * Created by Administrator on 2017/1/20.
 */

public class DensityUtils {

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */
    public static int dip2px(float dpValue) {
        final float scale = MyApplication.instance.getResources().getDisplayMetrics().density;
//        Toast.makeText(context,"density:"+scale,Toast.LENGTH_SHORT).show();
        return (int) (dpValue * scale );
    }

    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     * */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale );
    }
}
