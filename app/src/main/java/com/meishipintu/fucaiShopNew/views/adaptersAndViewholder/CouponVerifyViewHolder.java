package com.meishipintu.fucaiShopNew.views.adaptersAndViewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.meishipintu.fucaiShopNew.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/19.
 * <p>
 * 主要功能：
 */

public class CouponVerifyViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_amount)
    TextView tvName;
    @BindView(R.id.tv_time)
    TextView tvTime;

    public CouponVerifyViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
