package com.meishipintu.fucaiShopNew.views.adaptersAndViewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.meishipintu.fucaiShopNew.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/31.
 * <p>
 * 主要功能：
 */

public class PushInfoViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.order_name)
    TextView orderName;
    @BindView(R.id.phonenumber)
    TextView phonenumber;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.call)
    Button call;
    @BindView(R.id.sum)
    TextView sum;
    @BindView(R.id.kind)
    TextView kind;
    @BindView(R.id.bt_ignored)
    Button btIgnored;
    @BindView(R.id.bt_dograd)
    Button btDograd;

    public PushInfoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
