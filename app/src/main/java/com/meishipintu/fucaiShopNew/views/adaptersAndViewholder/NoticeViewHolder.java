package com.meishipintu.fucaiShopNew.views.adaptersAndViewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.meishipintu.fucaiShopNew.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/31.
 * <p>
 * 主要功能：
 */

public class NoticeViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.notification_title)
    TextView title;
    @BindView(R.id.notification_content)
    TextView content;

    public NoticeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
