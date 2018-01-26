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

public class RecommendSSQViewHolder extends RecommendationViewHolder {

    @BindView(R.id.red1)
    public TextView red1;
    @BindView(R.id.red2)
    public TextView red2;
    @BindView(R.id.red3)
    public TextView red3;
    @BindView(R.id.red4)
    public TextView red4;
    @BindView(R.id.red5)
    public TextView red5;
    @BindView(R.id.red6)
    public TextView red6;
    @BindView(R.id.red7)
    public TextView red7;
    @BindView(R.id.red8)
    public TextView red8;
    @BindView(R.id.red9)
    public TextView red9;
    @BindView(R.id.red10)
    public TextView red10;
    @BindView(R.id.blue1)
    public TextView blue1;
    @BindView(R.id.blue2)
    public TextView blue2;
    @BindView(R.id.blue3)
    public TextView blue3;

    public RecommendSSQViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
