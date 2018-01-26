package com.meishipintu.fucaiShopNew.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.NumberPicker;


/**
 * Created by Administrator on 2017/11/18.
 * <p>
 * 功能介绍：实现了定义显示数据为list的numberpicker
 */

public class CustomNumberPicker extends NumberPicker {
    /**
     * Create a new number picker.
     *
     * @param context The application environment.
     */
    public CustomNumberPicker(Context context) {
        super(context);
    }

    /**
     * Create a new number picker.
     *
     * @param context The application environment.
     * @param attrs   A collection of attributes.
     */
    public CustomNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Create a new number picker
     *
     * @param context      the application environment.
     * @param attrs        a collection of attributes.
     * @param defStyleAttr An attribute in the current theme that contains a
     *                     reference to a style resource that supplies default values for
     */
    public CustomNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
