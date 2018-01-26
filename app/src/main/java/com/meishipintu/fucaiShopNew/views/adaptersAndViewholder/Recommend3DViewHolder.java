package com.meishipintu.fucaiShopNew.views.adaptersAndViewholder;

import android.view.View;
import android.widget.TextView;

import com.meishipintu.fucaiShopNew.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/21.
 * <p>
 * 主要功能：
 */

public class Recommend3DViewHolder extends RecommendationViewHolder {

    @BindView(R.id.red1)
    public TextView red1;
    @BindView(R.id.red2)
    public TextView red2;
    @BindView(R.id.blue1)
    public TextView blue1;
    @BindView(R.id.blue2)
    public TextView blue2;
    @BindView(R.id.green1)
    public TextView green1;
    @BindView(R.id.green2)
    public TextView green2;


    public Recommend3DViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
