package com.meishipintu.fucaiShopNew.custom;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.meishipintu.fucaiShopNew.R;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017/3/21.
 * <p>
 * 主要功能：
 */

public class CustomNumPickeDialog extends PopupWindow {

    @BindView(R.id.np_first)
    CustomNumberPicker npFirst;
    @BindView(R.id.ok)
    TextView ok;

    private OnOkClickListener okListener;
    private Context context;

    public CustomNumPickeDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        initContext();
    }

    private void initContext() {
        View view = LayoutInflater.from(this.context).inflate(R.layout.dialog_picker, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(new ColorDrawable(0));
        // 设置进出动画
        this.setAnimationStyle(R.style.popAnimation);
    }

    public void setDataAndListener(final String[] data, OnOkClickListener listener) {
        this.okListener = listener;
        npFirst.setDisplayedValues(data);
        npFirst.setMinValue(0);
        npFirst.setMaxValue(data.length - 1);

        setNumberPickerDividerColor(npFirst,context);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okListener.onOkClick(npFirst.getValue());
                dismiss();
            }

        });
    }

    /**
     * 自定义滚动框分隔线颜色
     */
    private void setNumberPickerDividerColor(NumberPicker number,Context context) {
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    //设置分割线的颜色值
                    pf.set(number, new ColorDrawable(ContextCompat.getColor(context, R.color.theme_orange)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        okListener.onDismiss();
    }

    public interface OnOkClickListener {

        void onOkClick(int select);

        void onDismiss();
    }
}
