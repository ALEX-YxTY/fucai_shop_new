package com.meishipintu.fucaiShopNew.views.adaptersAndViewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.meishipintu.fucaiShopNew.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/17.
 * <p>
 * 主要功能：
 */

public class RewardViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_time)
    TextView tvTime;

    public RewardViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
