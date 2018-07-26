package com.meishipintu.fucaiShopNew.custom;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.meishipintu.fucaiShopNew.R;


public class CustomProgressDialog extends Dialog {

    public CustomProgressDialog(Context context, String strMessage) {  
        this(context, R.style.CustomProgressDialog, strMessage);  
    }  
  
    public CustomProgressDialog(Context context, int theme, String strMessage) {  
        super(context, theme);  
        this.setContentView(R.layout.cust_progress_dialog);
        this.getWindow().getAttributes().gravity = Gravity.CENTER;  
        ImageView im = (ImageView) findViewById(R.id.loadingImageView);
        Animation rotation = AnimationUtils.loadAnimation(context, R.anim.ani_rotate);
        rotation.setRepeatCount(Animation.INFINITE);
        im.startAnimation(rotation);
        TextView tvMsg = (TextView) this.findViewById(R.id.id_tv_loadingmsg);  
        if (tvMsg != null) {  
            tvMsg.setText(strMessage);  
        }  
    }  
  
    @Override  
    public void onWindowFocusChanged(boolean hasFocus) {
        if (!hasFocus) {  
            dismiss();  
        }  
    }  
}  

  

