package com.meishipintu.fucaiShopNew.views.adaptersAndViewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.meishipintu.fucaiShopNew.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/29.
 * <p>
 * 主要功能：
 */

public class RecommendationViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_period)
    public TextView tvPeriod;
    @BindView(R.id.tv_percentage)
    public TextView tvPercentage;

    public RecommendationViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
