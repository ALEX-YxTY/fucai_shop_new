package com.meishipintu.fucaiShopNew.custom;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
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

public class CustomDatePickeDialog extends PopupWindow {

    @BindView(R.id.date_picker)
    DatePicker datePicker;
    @BindView(R.id.ok)
    TextView ok;

    private OnDatePickListener okListener;
    private Context context;

    private int type = 1;   //1-选择年月  2-选择年月日

    public CustomDatePickeDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        initContext();
    }

    private void initContext() {
        View view = LayoutInflater.from(this.context).inflate(R.layout.dialog_date_picker, null);
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

    public void setListener(int type ,OnDatePickListener listener) {
        this.okListener = listener;
        this.type = type;

        setNumberPickerDividerColor(datePicker,context);
        if (type == 1) {
            hideDay(datePicker);
        }
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                okListener.onDatePick(CustomDatePickeDialog.this.type,datePicker.getYear(), datePicker.getMonth()
                        , datePicker.getDayOfMonth());
                dismiss();
            }

        });
    }

    private void hideDay(DatePicker mDatePicker) {
        try {
            /* 处理android5.0以上的特殊情况 */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int daySpinnerId = Resources.getSystem().getIdentifier("day", "id", "android");
                if (daySpinnerId != 0) {
                    View daySpinner = mDatePicker.findViewById(daySpinnerId);
                    if (daySpinner != null) {
                        daySpinner.setVisibility(View.GONE);
                    }
                }
            } else {
                Field[] datePickerfFields = mDatePicker.getClass().getDeclaredFields();
                for (Field datePickerField : datePickerfFields) {
                    if ("mDaySpinner".equals(datePickerField.getName()) || ("mDayPicker").equals(datePickerField.getName())) {
                        datePickerField.setAccessible(true);
                        Object dayPicker = new Object();
                        try {
                            dayPicker = datePickerField.get(mDatePicker);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        }
                        ((View) dayPicker).setVisibility(View.GONE);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 自定义滚动框分隔线颜色
     */
    private void setNumberPickerDividerColor(DatePicker number,Context context) {
        // 获取 mSpinners
        LinearLayout llFirst = (LinearLayout) number.getChildAt(0);

        // 获取 NumberPicker
        LinearLayout mSpinners = (LinearLayout) llFirst.getChildAt(0);
        for (int i = 0; i < mSpinners.getChildCount(); i++) {
            NumberPicker picker = (NumberPicker) mSpinners.getChildAt(i);

            Field[] pickerFields = NumberPicker.class.getDeclaredFields();
            for (Field pf : pickerFields) {
                if (pf.getName().equals("mSelectionDivider")) {
                    pf.setAccessible(true);
                    try {
                        pf.set(picker, new ColorDrawable(ContextCompat.getColor(context, R.color.theme_orange)));//设置分割线颜色
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        okListener.onDateDismiss();
    }

    public interface OnDatePickListener {

        void onDatePick(int type, int year, int month, int day);

        void onDateDismiss();
    }
}
